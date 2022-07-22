package dto;

public record UserFilter(int limit,
                         int offset,
                         Long phone,
                         String nick_name
                         ) {
}
