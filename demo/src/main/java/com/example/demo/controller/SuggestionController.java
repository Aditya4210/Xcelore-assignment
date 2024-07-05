package com.example.controller;

import com.example.entity.Doctor;
import com.example.entity.Patient;
import com.example.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/{patientId}")
    public ResponseEntity<?> suggestDoctors(@PathVariable Long patientId) {
        List<Doctor> doctors = patientService.suggestDoctors(patientId);
        if (doctors.isEmpty()) {
            Patient patient = patientService.getPatient(patientId);
            if (!List.of("Delhi", "Noida", "Faridabad").contains(patient.getCity())) {
                return ResponseEntity.ok("We are still waiting to expand to your location");
            }
            return ResponseEntity.ok("There isn’t any doctor present at your location for your symptom");
        }
        return ResponseEntity.ok(doctors);
    }
}
