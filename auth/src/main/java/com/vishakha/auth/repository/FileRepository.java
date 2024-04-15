package com.vishakha.auth.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class FileRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public int uploads(String file,String name)
    {
        System.out.println(name);
         return jdbcTemplate.update("INSERT INTO files (file_uploads,name) VALUES (?,?)",file,name);
    }
    public List<Map<String,Object>> fetchFiles()
    {
        return jdbcTemplate.queryForList("EXEC sp_get_files");
    }
}
