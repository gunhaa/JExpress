package simple.apiDocs;

import org.objectweb.asm.*;
import simple.mapper.Mapper;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ApiDocsDto {

    private List<ApiDetails> apiList = new ArrayList<>();
    private List<String> rawParam = new ArrayList<>();

//    @Deprecated
//    public void createProxy(Map<String, LambdaHandler> apiHandlers) {
//
//        for (Map.Entry<String, LambdaHandler> entry : apiHandlers.entrySet()) {
//            String url = entry.getKey();
//            LambdaHandler handler = entry.getValue();
//
//            HttpRequest mock = HttpRequest.createMock();
//            LambdaHttpRequest lambdaHttpRequest = new LambdaHttpRequest(mock);
//
//            LambdaHttpResponse response = new LambdaHttpResponse(lambdaHttpRequest, new PrintWriter(System.out, true));
//
//            handler.execute(null, response);
//
//            Class<?> returnType = LastSentObjectHolder.getLastSentType();
//            if (returnType != null) {
//                ApiDetails apiDetails = new ApiDetails(url, returnType.getSimpleName());
//
//                if(returnType.getPackage().getName().startsWith("java.util")){
//                    apiDetails.addField("collection", returnType.getSimpleName());
//                } else {
//                    Field[] fields = returnType.getDeclaredFields();
//                    for (Field field : fields) {
//                        apiDetails.addField(field.getName(), field.getType().getSimpleName());
//                    }
//                }
//                apiList.add(apiDetails);
//            }
//        }
//    }

    public void createApiDocs(Mapper getMap) {

        System.out.println("create apidocs 실행..");

        try {
            // todo : 빌드되는 클래스를 env로 가져와야 한다
            byte[] classBytes = Files.readAllBytes(Paths.get("build/classes/java/main/simple/Main.class"));
            ClassReader reader = new ClassReader(classBytes);

            reader.accept(new ClassVisitor(Opcodes.ASM9) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9) {
                        private boolean isSendMethod = false;
                        private String secondParamType = null;

                        // LDC 스택에 적재되는 값 추적
                        @Override
                        public void visitLdcInsn(Object value) {
                            if (value instanceof Type) {
                                secondParamType = ((Type) value).getTypeName();
                            } else if (value instanceof Class) {
                                secondParamType = ((Class<?>) value).getName();
                            } else {
                                secondParamType = String.valueOf(value);
                            }
                            // System.out.println("LDC 로드된 값: " + secondParamType);
                        }

                        // 이전 LDC에서 적재된 값을 활용하여 두 번째 파라미터 확인
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (name.equals("send")) {
                                isSendMethod = true;
                                // System.out.println("send() 호출: " + descriptor);
                                if (secondParamType != null) {
                                    System.out.println("send()의 두 번째 파라미터: " + secondParamType);
                                    rawParam.add(secondParamType);
                                }
                                secondParamType = null;
                            }
                        }
                    };
                }
            }, 0);

        } catch (Exception e) {
            System.err.println("asm library error");
        }

        parsingSecondParameter();
    }

    private String parsingSecondParameter(){
        for (String s : this.rawParam){

        }
        return
    }

    public List<ApiDetails> getApiList() {
        return this.apiList;
    }

};

