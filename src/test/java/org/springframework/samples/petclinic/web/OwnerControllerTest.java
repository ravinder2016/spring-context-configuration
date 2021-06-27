package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {


  @Autowired
  private OwnerController controller;

  @Autowired
  ClinicService clinicService;

  MockMvc mockMvc;

  @Captor
  ArgumentCaptor<String> argumentCaptor;

  @Mock
  BindingResult result;
/*
  @Mock
  Map<String, Object> model;
*/

  @Mock
  Model model;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @AfterEach
  void tearDown() {
    reset(clinicService);
  }

  @Test
  void testProcessCreationForm()
    throws Exception {
    mockMvc.perform(post("/owners/new")
        .param("address", "Thirumalagiri")
        .param("city", "Thirumalagiri")
        .param("telephone", "9676050840")
        .param("firstName", "Raveender")
        .param("lastName", "Karumula"))
      .andExpect(status().is3xxRedirection());
  }

  @Test
  void testProcessCreationFormFail()
    throws Exception {
    mockMvc.perform(post("/owners/new")
          .param("address", "Thirumalagiri")
          .param("city", "Thirumalagiri")
          .param("telephone", "9676050840"))
      .andExpect(status().isOk())
      .andExpect(model().attributeHasErrors("owner"))
      .andExpect(model().attributeHasFieldErrors("owner", "firstName"))
      .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
      .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
  }
  @Test
  void  testInitCreationForm()
    throws Exception {
    mockMvc.perform(get("/owners/new"))
      .andExpect(status().isOk())
      .andExpect(model().attributeExists("owner"))
      .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
  }

  @Test
  void testProcessFindForm()
    throws Exception {
    //Collection<Owner> results = new ArrayList<>();
    //given(clinicService.findOwnerByLastName(anyString())).willReturn(results);
    mockMvc.perform(get("/owners")
    .param("lastName", "Karumula"))
    .andExpect(status().isOk())
    .andExpect(view().name("owners/findOwners"));

  }


  @Test
  void testReturnListOwners()
    throws Exception {
    given(clinicService.findOwnerByLastName("")).willReturn(Lists.newArrayList(new Owner(), new Owner()));
    mockMvc.perform(get("/owners"))
      .andExpect(status().isOk())
      .andExpect(model().attributeExists("selections"))
      .andExpect(view().name("owners/ownersList"));

    then(clinicService).should().findOwnerByLastName(argumentCaptor.capture());

    assertThat(argumentCaptor.getValue().equalsIgnoreCase(""));
  }

  @Test
  void testReturnListWithOneOwner()
    throws Exception {
    Owner owner = new Owner();
    owner.setId(1);
    given(clinicService.findOwnerByLastName(anyString())).willReturn(Lists.newArrayList(owner));

    mockMvc.perform(get("/owners"))
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/1"));

    then(clinicService).should().findOwnerByLastName(anyString());
  }


  @Test
  void testProcessUpdateOwnerForm()
    throws Exception {
    mockMvc.perform(post("/owners/{ownerId}/edit", 1)
                      .param("address", "Thirumalagiri")
                      .param("city", "Thirumalagiri")
                      .param("telephone", "9676050840")
                      .param("firstName", "Raveender")
                      .param("lastName", "Karumula"))
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/{ownerId}"));
  }


  @Test
  void testProcessUpdateOwnerFormNotValid()
    throws Exception {
    mockMvc.perform(post("/owners/{ownerId}/edit", 1)
                      .param("address", "Thirumalagiri")
                      .param("city", "Thirumalagiri")
                      .param("telephone", "9676050840"))
      .andExpect(status().isOk())
      .andExpect(model().attributeHasErrors("owner"))
      .andExpect(model().attributeHasFieldErrors("owner", "firstName"))
      .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
      .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
  }

  @Test
  void testInitFindForm()
    throws Exception {
    mockMvc.perform(get("/owners/find"))
      .andExpect(status().isOk())
      .andExpect(model().attributeExists("owner"))
      .andExpect(view().name("owners/findOwners"));
  }

  @Test
  void testInitUpdateOwnerForm()
    throws Exception {
    Owner owner = new Owner();
    owner.setId(1);
    given(clinicService.findOwnerById(anyInt())).willReturn(owner);
    mockMvc.perform(get("/owners/{ownerId}/edit", 1))
      .andExpect(status().isOk())
      .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
  }
}