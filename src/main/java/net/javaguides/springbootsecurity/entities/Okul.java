package net.javaguides.springbootsecurity.entities;

import lombok.Data;
import net.javaguides.springbootsecurity.enums.Ilce;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name="OKUL")
@Data
public class Okul extends BaseEntity
{
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Ilce ilce;
}
