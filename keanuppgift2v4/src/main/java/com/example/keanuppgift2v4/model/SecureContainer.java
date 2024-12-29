package com.example.keanuppgift2v4.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "timecapsulemessages")
public class SecureContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "encrypted_message", nullable = false)
    private String encryptedNote;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AccountData owner;

    public Long getId() {
        return id;
    }
    public void setId(Long val) {
        this.id = val;
    }

    public String getEncryptedNote() {
        return encryptedNote;
    }
    public void setEncryptedNote(String val) {
        this.encryptedNote = val;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime val) {
        this.createdAt = val;
    }

    public AccountData getOwner() {
        return owner;
    }
    public void setOwner(AccountData val) {
        this.owner = val;
    }
}
