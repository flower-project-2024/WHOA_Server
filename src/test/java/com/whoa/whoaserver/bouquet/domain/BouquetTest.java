package com.whoa.whoaserver.bouquet.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.whoa.whoaserver.global.exception.BadRequestException;
import com.whoa.whoaserver.member.domain.Member;

@ExtendWith(MockitoExtension.class)
class BouquetTest {

    @InjectMocks
    private Bouquet bouquet;

    @Mock
    private Member member;

    @Test
    void setMember_whenMemberIsNull_shouldSetMember() {
        // Given
        lenient().when(member.getId()).thenReturn(1L);

        // When
        bouquet.setMember(member);

        // Then
        assertEquals(member, bouquet.getMember());
    }

    @Test
    void setMember_whenMemberIsNotNullAndDifferent_shouldThrowException() {
        // Given
        lenient().when(member.getId()).thenReturn(1L);
        bouquet.setMember(member);

        Member newMember = new Member();

        // When, Then
        assertThrows(BadRequestException.class, () -> bouquet.setMember(newMember));
    }

    @Test
    void setMember_whenMemberIsNotNullAndSame_shouldNotThrowException() {
        // Given
        lenient().when(member.getId()).thenReturn(1L);
        bouquet.setMember(member);

        // When
        bouquet.setMember(member);

        // Then
        assertEquals(member, bouquet.getMember());
    }

}
