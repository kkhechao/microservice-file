import com.zqkh.file.context.Application;
import com.zqkh.file.context.appservice.inter.FileService;
import com.zqkh.file.eventdto.FileIndexDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@MapperScan(basePackages = { "com.zqkh.file.context.appservice.impl.domain.repository.mappers" })
public class FileServiceTest {


    @Autowired
    private FileService fileService;

    @Test
    public void uploadFile() {
        String s = "BBBcccc";
        FileIndexDto fileIndexDto = fileService.updateFile(s.getBytes(), "text/plain", null);
        System.out.println(fileIndexDto.toString());
    }

    @Test
    public void appendUsedRecord() {
        String id = "bbcc5ca1815faa2f82f0564be2b23b85";
        fileService.appendUsedRecord(new String[]{id}, "bizP", "bizId");
    }

    @Test
    public void clearUnusedFiles() {
        fileService.clearUnusedFiles(new Date());
    }


}
