package com.demo.inventory.admin.service;

import com.demo.inventory.admin.dto.UserStatisticsResponse;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.security.DbRole;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final ItemRepository itemRepository;

    public List<UserStatisticsResponse> getUserStatistics() {
        List<UserStatisticsResponse> result = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        List<User> users = allUsers.stream()
                .filter(u -> !u.getRole().equals(DbRole.ADMIN))
                .collect(Collectors.toList());
        users.forEach(user -> {
            UserStatisticsResponse response = getStatisticsResponseByUser(user);
            result.add(response);
        });
        return result;
    }

    private UserStatisticsResponse getStatisticsResponseByUser(User user) {
        String username = user.getUsername();
        Long userId = user.getUserId();
        Date userRegisteredAt = user.getDateRegistered();
        List<Item> userItems = itemRepository.findAllByUserId(userId);
        int userNumOfItems = userItems.size();
        int userNumOfFolders = folderRepository.findAllByUserId(userId).size();
        Date userLastItemAddedAt = null;
        if (userNumOfItems > 0) {
            userItems.sort(Comparator.comparing(Item::getDateAdded));
            userLastItemAddedAt = userItems.get(userItems.size() - 1).getDateAdded();
        }
        return UserStatisticsResponse
                .builder()
                .username(username)
                .numOfItems(userNumOfItems)
                .numOfFolders(userNumOfFolders)
                .registeredAt(userRegisteredAt)
                .lastItemAddedAt(userLastItemAddedAt)
                .build();
    }
}
