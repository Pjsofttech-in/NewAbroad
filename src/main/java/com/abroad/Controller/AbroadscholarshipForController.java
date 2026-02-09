package com.abroad.Controller;

import com.abroad.Entity.AbroadscholarshipFor;
import com.abroad.Service.AbroadscholarshipForService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadscholarshipForController {



    @Autowired
    private  final AbroadscholarshipForService service;


    public AbroadscholarshipForController(AbroadscholarshipForService service) {
        this.service = service;

    }

        @PostMapping("/createScholarshipFor")
        public ResponseEntity<AbroadscholarshipFor> create(@RequestBody AbroadscholarshipFor abroadscholarshipFor)
        {
            return  new ResponseEntity<>(service.create(abroadscholarshipFor), HttpStatus.CREATED);

        }

    @GetMapping("/GetByAllScholarshipFor")
    public List<AbroadscholarshipFor> getByAll(){
        return  service.getAll();
    }


    @GetMapping("/GetByIdScholarshipFor/{id}")
    public  ResponseEntity<AbroadscholarshipFor> getByid(@PathVariable Long id)
    {
        return  ResponseEntity.ok(service.GetById(id));
    }

    @PutMapping("/UpdateScholarshipFor/{id}")
    public  ResponseEntity<AbroadscholarshipFor> update(@RequestBody AbroadscholarshipFor abroadscholarshipFor, @PathVariable Long id)
    {
      return  ResponseEntity.ok(service.updateScholarship(abroadscholarshipFor, id));
    }


    @DeleteMapping("/deleteScholarship/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id)
    {
        service.deleteScholarship(id);
        return  ResponseEntity.ok("Delete Successfully..!!");
    }
}
