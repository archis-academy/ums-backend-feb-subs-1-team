package com.archisacadeny.instructor;

import java.util.List;

public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public Instructor createInstructor(Instructor instructor) {
        if(instructor== null){
            throw new RuntimeException("instructor is null");
        }
        return instructorRepository.save(instructor);
    }

    public List<Instructor> listAllInstructors() {
        return instructorRepository.listAllInstructors();
    }

    public Instructor getInstructorById(long instructorId){
        return instructorRepository.getInstructorById(instructorId);
    }

    public Instructor updateInstructor(Instructor instructor) {
        if(instructor == null){
            throw new RuntimeException("instructor is null");
        }
        return instructorRepository.updateInstructor(instructor);
    }

    public void deleteInstructor(long instructorId) {
        instructorRepository.deleteInstructor(instructorId);
    }

    public Instructor createInstructor(long id, String instructorNumber, String fullName, String email, String password){

        Instructor instructor = new Instructor();
        instructor.setId(id);
        instructor.setNumber(instructorNumber);
        instructor.setFullName(fullName);
        instructor.setEmail(email);
        instructor.setPassword(password);

        return instructorRepository.save(instructor);
    }
}
