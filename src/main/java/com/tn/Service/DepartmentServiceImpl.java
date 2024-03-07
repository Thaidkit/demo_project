package com.tn.Service;

import com.tn.Entity.Department;
import com.tn.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = departmentRepository.findAll();
        return departments;
    }

    @Override
    public boolean save(Department department) {
        departmentRepository.save(department);
        return true;
    }

    @Override
    public boolean delete(int id){
        departmentRepository.deleteById(id);
        return true;
    }

    @Override
    public Department getById(Integer id){
        Department department = departmentRepository.findById(id).get();
        return department;
    }
}
