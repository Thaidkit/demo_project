package com.tn.Service;

import com.tn.Entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAll();

    boolean save(Department department);

    Department getById(Integer id);
    boolean delete(int id);
}
