package ru.itmo.kotiki2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itmo.kotiki2.enums.CatColor;
import ru.itmo.kotiki2.enums.CatType;
import ru.itmo.kotiki2.model.ModelCat;
import ru.itmo.kotiki2.model.ModelOwner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class MainServiceTest {
    @Mock
    OwnerService ownerService;
    @Mock
    CatService catService;
    @InjectMocks
    MainService mainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCat() throws Exception {
        when(ownerService.findOwnerById(anyInt())).thenReturn(null);
        when(ownerService.addCat(any(), any())).thenReturn(null);
        when(catService.createCat(any())).thenReturn(null);

        ModelCat result = mainService.registerCat("name", LocalDate.of(2022, Month.JUNE, 3),
                CatType.PER, CatColor.BLACK, 0, Arrays.<ModelCat>asList(null));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testRegisterOwner() throws Exception {
        when(ownerService.createOwner(any())).thenReturn(null);

        ModelOwner result = mainService.registerOwner("name", "surname", "mail", LocalDate.of(2022, Month.JUNE, 3), Arrays.<ModelCat>asList(null));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetCatsOfOwner() throws Exception {
        when(ownerService.findCatsOfOwner(anyInt())).thenReturn(Arrays.<ModelCat>asList(null));

        List<ModelCat> result = mainService.getCatsOfOwner(0);
        Assertions.assertEquals(Arrays.<ModelCat>asList(null), result);
    }

    @Test
    void testGetFriendsOfCat() throws Exception {
        when(catService.getFriendsOfCat(anyInt())).thenReturn(Arrays.<ModelCat>asList(null));

        List<ModelCat> result = mainService.getFriendsOfCat(0);
        Assertions.assertEquals(Arrays.<ModelCat>asList(null), result);
    }

    @Test
    void testFriend2Cats() throws Exception {
        mainService.friendCats(0, 0);
    }

    @Test
    void testUnfriend2Cats() throws Exception {
        mainService.unfriendCats(0, 0);
    }

    @Test
    void testFriendCats() throws Exception {
        mainService.friendCats(Arrays.<Integer>asList(Integer.valueOf(0)));
    }

    @Test
    void testUnfriendCats() throws Exception {
        mainService.unfriendCats(Arrays.<Integer>asList(Integer.valueOf(0)));
    }

    @Test
    void testChangeMailOfOwner() throws Exception {
        when(ownerService.changeMail(anyInt(), anyString())).thenReturn(null);

        ModelOwner result = mainService.changeMailOfOwner(0, "mail");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testChangeNameOfCat() throws Exception {
        when(catService.changeNameOfCat(anyInt(), anyString())).thenReturn(null);

        ModelCat result = mainService.changeNameOfCat(0, "name");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testChangeOwnerOfCat() throws Exception {
        when(ownerService.getOwnerById(anyInt())).thenReturn(null);
        when(catService.findCatById(anyInt())).thenReturn(null);
        when(catService.isCatExist(anyInt())).thenReturn(true);
        when(catService.changeOwnerOfCat(anyInt(), any())).thenReturn(null);

        ModelCat result = mainService.changeOwnerOfCat(0, 0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testFindCatById() {
        when(catService.findCatById(anyInt())).thenReturn(null);

        ModelCat result = mainService.findCatById(0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetCatById() {
        when(catService.findCatById(anyInt())).thenReturn(null);

        ModelCat result = mainService.getCatById(0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testFindCatsById() {
        when(catService.findCatById(anyInt())).thenReturn(null);

        List<ModelCat> result = mainService.findCatsById(Arrays.<Integer>asList(Integer.valueOf(0)));
        Assertions.assertEquals(Arrays.<ModelCat>asList(null), result);
    }

    @Test
    void testFindOwnerById() {
        when(ownerService.findOwnerById(anyInt())).thenReturn(null);

        ModelOwner result = mainService.findOwnerById(0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetOwnerById() {
        when(ownerService.getOwnerById(anyInt())).thenReturn(null);

        ModelOwner result = mainService.getOwnerById(0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testFindOwnersById() {
        when(ownerService.findOwnerById(anyInt())).thenReturn(null);

        List<ModelOwner> result = mainService.findOwnersById(Arrays.<Integer>asList(Integer.valueOf(0)));
        Assertions.assertEquals(Arrays.<ModelOwner>asList(null), result);
    }

    @Test
    void testGetAllCats() {
        when(catService.findAllCats()).thenReturn(Arrays.<ModelCat>asList(null));

        List<ModelCat> result = mainService.getAllCats();
        Assertions.assertEquals(Arrays.<ModelCat>asList(null), result);
    }

    @Test
    void testGetAllOwners() {
        when(ownerService.findAllOwners()).thenReturn(Arrays.<ModelOwner>asList(null));

        List<ModelOwner> result = mainService.getAllOwners();
        Assertions.assertEquals(Arrays.<ModelOwner>asList(null), result);
    }

    @Test
    void testDeleteCatById() {
        mainService.deleteCatById(0);
    }

    @Test
    void testDeleteOwnerById() {
        mainService.deleteOwnerById(0);
    }

    @Test
    void testDeleteCatsById() {
        mainService.deleteCatsById(Arrays.<Integer>asList(Integer.valueOf(0)));
    }

    @Test
    void testDeleteOwnersById() {
        mainService.deleteOwnersById(Arrays.<Integer>asList(Integer.valueOf(0)));
    }

    @Test
    void testDeleteAllOwners() {
        mainService.deleteAllOwners();
    }

    @Test
    void testDeleteAllCats() {
        mainService.deleteAllCats();
    }
}