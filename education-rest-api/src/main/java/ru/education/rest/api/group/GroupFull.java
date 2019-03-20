package ru.education.rest.api.group;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.education.rest.api.Dto;
import ru.education.rest.api.RepositoryDto;
import ru.education.rest.api.role.RoleFull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonRootName("group")
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class GroupFull extends RepositoryDto implements Dto, Serializable {

    private String codeName;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<RoleFull> roles;

    public GroupFull() {
        super();
    }

    public GroupFull(GroupFull source) {
        super(source);
        if (source == null) {
            return;
        }
        setCodeName(source.getCodeName());
        if (source.getRoles() != null) {
            for (RoleFull item : source.getRoles()) {
                addRole(new RoleFull(item));
            }
        }
    }

    public void addRole(RoleFull role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }
}
