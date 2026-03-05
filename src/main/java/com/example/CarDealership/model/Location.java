package com.example.CarDealership.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;        // e.g. "Kigali", "Gasabo", "Kimironko"

    @Column(nullable = false)
    private String type;        // "PROVINCE", "DISTRICT", "SECTOR", "CELL", "VILLAGE"

    // Self-referencing — parent is null for Province
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Location parent;

    // Children of this location
    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<Location> children;

    // ─── Getters & Setters ──────────────────────

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Location getParent() { return parent; }
    public void setParent(Location parent) { this.parent = parent; }

    public List<Location> getChildren() { return children; }
    public void setChildren(List<Location> children) { this.children = children; }
}