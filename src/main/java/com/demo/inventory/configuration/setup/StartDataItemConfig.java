package com.demo.inventory.configuration.setup;

import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
@AllArgsConstructor
public class StartDataItemConfig {

    private final ItemRepository itemRepository;

    protected void addStartItemDataIfNeeded() {
            Item computer = Item.builder().itemName("Computer").folderId(null).userId(2L).categoryId(1L).dateAdded(new Timestamp(System.currentTimeMillis())).description("Modern and fast computer.").serialNumber("12345678").itemPrice(999.99f).build();
            Item ukulele = Item.builder().itemName("Ukulele").folderId(null).userId(2L).categoryId(null).dateAdded(new Timestamp(System.currentTimeMillis())).description("Musical instrument.").serialNumber(null).itemPrice(null).build();
            Item cleanCodeBook = Item.builder().itemName("Clean Code Book").folderId(null).userId(2L).categoryId(4L).dateAdded(new Timestamp(System.currentTimeMillis())).description("Book about clean code.").serialNumber(null).itemPrice(12.99f).build();
            Item hammer = Item.builder().itemName("Hammer").folderId(null).userId(2L).categoryId(2L).dateAdded(new Timestamp(System.currentTimeMillis())).description(null).serialNumber(null).itemPrice(null).build();
            Item map = Item.builder().itemName("Map").folderId(null).userId(2L).categoryId(null).dateAdded(new Timestamp(System.currentTimeMillis())).description("Map of Tallinn.").serialNumber(null).itemPrice(null).build();
            Item disc = Item.builder().itemName("Disc").folderId(6L).userId(2L).categoryId(3L).dateAdded(new Timestamp(System.currentTimeMillis())).description("Disc with music.").serialNumber("987678765").itemPrice(null).build();
            Item secret = Item.builder().itemName("Secret").folderId(11L).userId(2L).categoryId(null).dateAdded(new Timestamp(System.currentTimeMillis())).description("It's a secret").serialNumber("98767873").itemPrice(null).build();
            Item wire = Item.builder().itemName("Wire").folderId(13L).userId(2L).categoryId(1L).dateAdded(new Timestamp(System.currentTimeMillis())).description("A wire.").serialNumber("234563425678").itemPrice(11.99f).build();
            Item dorianGreyBook = Item.builder().itemName("Dorian Grey Book").folderId(2L).userId(2L).categoryId(4L).dateAdded(new Timestamp(System.currentTimeMillis())).description("A book about Dorian Grey.").serialNumber(null).itemPrice(null).build();
            Item flower = Item.builder().itemName("Flower").folderId(null).userId(3L).categoryId(6L).dateAdded(new Timestamp(System.currentTimeMillis())).description(null).serialNumber(null).itemPrice(null).build();
            Item headphones = Item.builder().itemName("Headphones").folderId(null).userId(3L).categoryId(null).dateAdded(new Timestamp(System.currentTimeMillis())).description(null).serialNumber("4567876543").itemPrice(null).build();
            Item cup = Item.builder().itemName("Cup").folderId(14L).userId(3L).categoryId(5L).dateAdded(new Timestamp(System.currentTimeMillis())).description("Facny cup.").serialNumber(null).itemPrice(null).build();
            Item notebook = Item.builder().itemName("Notebook").folderId(null).userId(5L).categoryId(null).dateAdded(new Timestamp(System.currentTimeMillis())).description(null).serialNumber(null).itemPrice(null).build();
            itemRepository.saveAll(List.of(computer, ukulele, cleanCodeBook, hammer, map, disc, secret, wire, dorianGreyBook, flower, headphones, cup, notebook));
    }
}
