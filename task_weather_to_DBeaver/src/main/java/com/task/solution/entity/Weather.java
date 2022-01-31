package com.task.solution.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "weather_history")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_id")
    Long id;
    @Column(name = "weather_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date date;
    @Column(name = "weather_time")
    @Temporal(TemporalType.TIMESTAMP)
    Date time;
    @Column(name = "weather_value")
    String value;
    @JoinColumn(name = "weather_location")
    @OneToOne()
    Location location;



}
