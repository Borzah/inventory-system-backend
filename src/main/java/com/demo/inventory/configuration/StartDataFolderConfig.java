package com.demo.inventory.configuration;

import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@AllArgsConstructor
public class StartDataFolderConfig {

    private final FolderRepository folderRepository;

    public void addStartFolderDataIfNeeded() {
            Folder tuba1 = new Folder("Tuba-1", null, 2L);//1
            Folder tuba2 = new Folder("Tuba-2", null, 2L);//2
            Folder tuba3 = new Folder("Tuba-3", null, 2L);//3
            Folder tuba4 = new Folder("Tuba-4", null, 2L);//4
            Folder music = new Folder("Music", null, 2L);//5
            Folder discs = new Folder("Discs", 5L, 2L);//6
            Folder osakond3 = new Folder("Osakond-3", null, 2L);//7
            Folder ladu6 = new Folder("Ladu-6", 7L, 2L);//8
            Folder rida1 = new Folder("Rida-4", 8L, 2L);//9
            Folder kast6 = new Folder("Kast-6", 9L, 2L);//10
            Folder karp2 = new Folder("Karp-2", 10L, 2L);//11
            Folder karp5 = new Folder("Karp-5", 1L, 2L);//12
            Folder kaust2 = new Folder("Kaust-2", 12L, 2L);//13
            Folder box = new Folder("box", null, 3L);//14
            folderRepository.saveAll(List.of(tuba1, tuba2, tuba3, tuba4, music, discs,
                    osakond3, ladu6, rida1, kast6, karp2, karp5, kaust2, box));
    }
}

