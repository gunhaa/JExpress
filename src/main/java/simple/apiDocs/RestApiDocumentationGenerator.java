package simple.apiDocs;

import jakarta.persistence.OneToMany;
import simple.constant.CustomHttpMethod;
import simple.mapper.IMapper;
import simple.lambda.LambdaHandlerWrapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class RestApiDocumentationGenerator {

    public List<ApiDetail> extractApiDetails(IMapper IMapper) {

        List<ApiDetail> apiList = new ArrayList<>();

        Map<String, LambdaHandlerWrapper> apiHandlers = IMapper.getHandlers();
        CustomHttpMethod method = IMapper.getMethod();

        for (Map.Entry<String, LambdaHandlerWrapper> entry : apiHandlers.entrySet()) {
            String url = entry.getKey();
            Class<?> returnClazz = entry.getValue().getClazz();

            ApiDetail apiDetail = new ApiDetail(method, url, returnClazz.getSimpleName());

            if (returnClazz.isPrimitive() || returnClazz.isArray() ||
                    (returnClazz.getPackage() != null && returnClazz.getPackage().getName().startsWith("java"))) {
                apiDetail.addField(returnClazz);
            } else {
                Field[] fields = returnClazz.getDeclaredFields();
                for (Field field : fields) {
//                    if (field.isAnnotationPresent(ManyToOne.class) ||
//                            field.isAnnotationPresent(OneToMany.class) ||
//                            field.isAnnotationPresent(OneToOne.class) ||
//                            field.isAnnotationPresent(ManyToMany.class)) {
//                        continue;
//                    }
                    if(field.isAnnotationPresent(OneToMany.class)){
                        continue;
                    }
                    apiDetail.addField(field.getName(), field.getType().getSimpleName());
                }
            }
            apiList.add(apiDetail);
        }
        return apiList;
    }
    /*
         private final List<String> lambdaParam = new ArrayList<>();

    @Deprecated
    public void createProxy(Map<String, LambdaHandler> apiHandlers) {

        for (Map.Entry<String, LambdaHandler> entry : apiHandlers.entrySet()) {
            String url = entry.getKey();
            LambdaHandler handler = entry.getValue();

            HttpRequest mock = HttpRequest.createMock();
            LambdaHttpRequest lambdaHttpRequest = new LambdaHttpRequest(mock);

            LambdaHttpResponse response = new LambdaHttpResponse(lambdaHttpRequest, new PrintWriter(System.out, true));

            handler.execute(null, response);

            Class<?> returnType = LastSentObjectHolder.getLastSentType();
            if (returnType != null) {
                ApiDetails apiDetails = new ApiDetails(url, returnType.getSimpleName());

                if(returnType.getPackage().getName().startsWith("java.util")){
                    apiDetails.addField("collection", returnType.getSimpleName());
                } else {
                    Field[] fields = returnType.getDeclaredFields();
                    for (Field field : fields) {
                        apiDetails.addField(field.getName(), field.getType().getSimpleName());
                    }
                }
                apiList.add(apiDetails);
            }
        }
    }


    @Deprecated
    public void createApiDocsByteCode(IMapper getMap) {

        try {
            // 빌드되는 클래스를 env로 가져와야 한다
            // 뭘 하든 사용자가 불편함을 감수해야함.. 완벽하게 프레임워크 혼자서 만들어내는 방법 없을까..
            // 도저히 안되겠음 사용자가 뭔가 해줘야함

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
                            System.out.println("LDC 로드된 값: " + secondParamType);
                        }

                        // 이전 LDC에서 적재된 값을 활용하여 두 번째 파라미터 확인
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

                            if (name.equals("get")) {
//                                System.out.println("get : " + name);
                            }

                            if (name.equals("send")) {
                                isSendMethod = true;
                                System.out.println("send() 호출: " + opcode);
                                System.out.println("send() 호출: " + owner);
                                System.out.println("send() 호출: " + name);
                                System.out.println("send() 호출: " + descriptor);
                                if (secondParamType != null) {
                                    System.out.println("send()의 두 번째 파라미터: " + secondParamType);
                                    lambdaParam.add(secondParamType);
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

        parsingParam();
        System.out.println(this.lambdaParam);

        Map<String, LambdaHandler> apiHandlers = getMap.getHandlers();

        for (Map.Entry<String, LambdaHandler> entry : apiHandlers.entrySet()) {
            String url = entry.getKey();
            CustomHttpMethod method = getMap.getMethod();

            ApiDetails apiDetails = new ApiDetails(method, url, );
            apiList.add(apiDetails);
        }
    }

    @Deprecated
    private void parsingParam() {
        for (int i = 0; i < this.lambdaParam.toArray().length; i++) {
            String head = lambdaParam.get(i);
            String delHead = head.replace(";", "");
            String[] split = delHead.split("/");
            String s = split[split.length - 1];
            lambdaParam.set(i, s);
        }
        Collections.reverse(this.lambdaParam);
    }
    */
}