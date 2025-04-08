package simple.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simple.constant.ApplicationSettingFlags;
import simple.constant.ServerSettingChecker;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static simple.constant.ApplicationSettingFlags.*;

class ApplicationConfigTest {

    @Test
    public void 단일_설정_등록_테스트(){
        assertFalse(ServerSettingChecker.isServerEnabled(CORS));

        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.registerConfig(CORS);

        assertTrue(ServerSettingChecker.isServerEnabled(CORS));
    }

    @Test
    public void 다중_설정_등록_테스트(){
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();

        ApplicationSettingFlags[] settings = { CORS, API_DOCS, RESPONSE_TIME };

        for (ApplicationSettingFlags setting : settings) {
            assertFalse(ServerSettingChecker.isServerEnabled(setting), setting.name() + "는 등록 전이므로 비활성 상태여야 함");
        }

        for (ApplicationSettingFlags setting : settings) {
            applicationConfig.registerConfig(setting);
        }

        for (ApplicationSettingFlags setting : settings) {
            assertTrue(ServerSettingChecker.isServerEnabled(setting), setting.name() + "가 정상적으로 등록되어야 함");
        }
    }

    @Test
    public void H2_MySQL_동시_설정시_H2_설정_자동끄기(){
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.registerConfig(DB_H2);
        applicationConfig.registerConfig(DB_MYSQL);

        int config = applicationConfig.getConfig();

        boolean useH2AndMySQL = ApplicationSettingFlags.isH2AndMySQLEnabled(config);
        System.out.println(useH2AndMySQL);
    }

    @BeforeEach
    public void 설정_초기화(){
        ApplicationConfig.getInstance().clearConfigs();
    }
}