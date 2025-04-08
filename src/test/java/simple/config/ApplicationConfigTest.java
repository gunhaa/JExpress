package simple.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simple.constant.ApplicationSettingFlags;
import simple.constant.ServerSettingChecker;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static simple.constant.ApplicationSettingFlags.CORS;
import static simple.constant.ApplicationSettingFlags.API_DOCS;
import static simple.constant.ApplicationSettingFlags.RESPONSE_TIME;
import static simple.constant.ApplicationSettingFlags.DB_H2;
import static simple.constant.ApplicationSettingFlags.DB_MYSQL;

class ApplicationConfigTest {

    @Test
    @DisplayName("단일 설정 등록 테스트")
    public void testSingleSettingRegistration(){
        assertFalse(ServerSettingChecker.isServerEnabled(CORS));

        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.registerConfig(CORS);

        assertTrue(ServerSettingChecker.isServerEnabled(CORS));
    }

    @Test
    @DisplayName("다중 설정 등록 테스트")
    public void testMultipleSettingsRegistration(){
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
    @DisplayName("여러 DB 사용 시 설정 등록 및 설정 끄기 테스트")
    public void testRegisterAndDisableMultipleDbSettings(){
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        int defaultConfig = applicationConfig.getConfig();
        boolean defaultConfigBoolean = ServerSettingChecker.isH2AndMySQLDisabled(defaultConfig);

        Assertions.assertTrue(defaultConfigBoolean);

        applicationConfig.registerConfig(DB_H2);
        applicationConfig.registerConfig(DB_MYSQL);

        int registerConfig = applicationConfig.getConfig();

        boolean useH2MySQLConfig = ServerSettingChecker.isH2AndMySQLEnabled(registerConfig);
        Assertions.assertTrue(useH2MySQLConfig);

        applicationConfig.unRegisterConfig(DB_H2);

        int unRegisterConfig = applicationConfig.getConfig();
        boolean useMySQLConfig = ServerSettingChecker.isH2AndMySQLEnabled(unRegisterConfig);
        Assertions.assertFalse(useMySQLConfig);
    }

    @BeforeEach
    public void clearConfigs(){
        ApplicationConfig.getInstance().clearConfigs();
    }
}