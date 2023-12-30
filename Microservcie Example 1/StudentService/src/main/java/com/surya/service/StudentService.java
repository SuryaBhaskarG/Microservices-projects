package com.surya.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.surya.entity.Student;
import com.surya.exception.StudentNotFoundException;
import com.surya.repository.StudentRepository;
import com.surya.vo.Department;



@Service
public class StudentService 
{
	
	Logger logger = LoggerFactory.getLogger(StudentService.class);
	
	//private String baseUrl = "http://localhost:8080/department/";
	private String baseUrl = "http://DEPARTMENTSERVICE:8080/department/";
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Student saveStudent(Student student) 
	{
		return studentRepository.save(student);
	}

	public String getStudentWithDepartment(Long id) 
	{
		//Student student = studentRepository.findById(id).get();
		Student student = studentRepository.findById(id)
				   .orElseThrow(() -> new StudentNotFoundException(id));
		Department department = restTemplate.getForObject(baseUrl+student.getId(), Department.class);
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		buffer.append(student.getStudentId()+"  "+student.getName()+ "  "+student.getEmail()+"  "+student.getId());
		buffer.append("\n");
		buffer.append(department.getDepartmentName()+"  "+department.getDepartmentCode()+"  "+department.getDepartmentAddress());
		logger.info("Student with department details :"+buffer.toString());
		return buffer.toString();
	}
	
}
