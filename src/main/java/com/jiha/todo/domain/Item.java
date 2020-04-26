package com.jiha.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String contents;

    @ManyToMany
    @JoinTable(name = "item_ref",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "item_ref_id"))
    private List<Item> refItems = new ArrayList<Item>();

    private boolean completeYn;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    public void addRefItem(Long refItemId){
        refItems.add(Item.builder().id(refItemId).build());
    }
}
