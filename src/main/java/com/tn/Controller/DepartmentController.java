package com.tn.Controller;

import com.tn.DTO.DepartmentDTO;
import com.tn.Entity.Department;
import com.tn.Repository.DepartmentRepository;
import com.tn.Service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @GetMapping("department")
    public String getAll(Model model){
        List<Department> departments = departmentService.getAll();
        List<DepartmentDTO> departmentDTOS = new ArrayList<>();

        departments.forEach(department -> {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(department.getId());
            departmentDTO.setDepartmentName(department.getDepartmentName());
            departmentDTO.setDescription(department.getDescription());

            departmentDTOS.add(departmentDTO);
        });
        model.addAttribute("departmentDTOS", departmentDTOS);
        return "department_list";
    }

    @GetMapping("department/add")
    public String add(){
        return "add_department";
    }

    @PostMapping ("department/save")
    public String save(@RequestParam String departmentName,
                       @RequestParam String description){

        Department department = new Department(departmentName, description);
        departmentService.save(department);
        return "redirect:/department";
    }

    @GetMapping("department/delete/{id}")
    public String delete(@PathVariable int id){
        departmentService.delete(id);
        return "redirect:/department";
    }
}
