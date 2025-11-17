package io.github.ksoes.category.dto;

import io.github.ksoes.common.domain.YesOrNo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryForm {
    List<CreateList> createData;
    List<UpdateList> updateData;
    List<DeleteList> deleteData;

    @Getter
    @Setter
    public static class CreateList {
        private String categoryName;
        private YesOrNo useYn;
    }

    @Getter
    @Setter
    public static class UpdateList {
        private Long id;
        private String categoryName;
        private YesOrNo useYn;
    }

    @Getter
    @Setter
    public static class DeleteList {
        private Long id;
    }
}
