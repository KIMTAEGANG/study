package com.study.module;


import com.study.RestDocumentConfig;
import com.study.module.user.adapter.input.UserApiController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserApiController.class)
@AutoConfigureRestDocs
public class UserApiControllerTest extends RestDocumentConfig {

    private static String signUp = "/user/signUp";
    @Test
    @DisplayName("회원 가입")
    void register() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                post(signUp)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andDo(
                document(
                        "{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                )

        ).andExpect(status().isCreated());
    }
}
