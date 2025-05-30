package com.whoa.whoaserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
	OBJECT_MAPPER_JSON_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "json 처리 도중 에러가 발생했습니다."),

	SCHEDULER_CLIENT_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "스케쥴러로 외부 API를 호출할 때 whoa server에서 보내는 request가 올바르지 않습니다."),
	SCHEDULER_FLOWER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "화훼 유통 정보 시스템 사이트에서 response를 받아올 수 없습니다."),
	SCHEDULER_WHOA_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "스케쥴러 동작 중 whoa server에서 에러가 발생했습니다."),

	EXIST_MEMBER(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
	INVALID_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),

	FLOWER_EXPRESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 flowerExpression id입니다."),
	INVALID_FLOWER_AND_EXPRESSION(HttpStatus.NOT_FOUND, "꽃에 대응되는 꽃말이 없습니다."),
	NOT_REGISTER_BOUQUET(HttpStatus.NOT_FOUND, "등록되지 않은 꽃다발 주문서입니다."),
	NOT_MEMBER_BOUQUET(HttpStatus.CONFLICT, "해당 유저가 주문한 꽃다발이 아닙니다."),
	DUPLICATED_BOUQUET_NAME(HttpStatus.CONFLICT, "해당 유저가 이미 동일한 꽃다발 주문서 이름으로 등록한 이력이 있습니다."),

	INVALID_MATCHING_WITH_CUSTOMIZING_PURPOSE_AND_KEYWORD(HttpStatus.NOT_FOUND, "구매 목적에 대응되는 꽃말의 키워드가 업습니다."),
	INVALID_MATCHING_WITH_BOUQUET_SELECTED_COLORS_AND_FLOWEREXPRESSION_FLOWER_COLORS(HttpStatus.NOT_FOUND, "원하는 꽃다발 색상에 부합하는 색깔을 가진 꽃이 데이터베이스에 없습니다."),
	INVALID_MATCHING_WITH_BOUQUET_SELECTED_COLORS_AND_FLOWER_COLORS_AND_KEYWORD_AND_CUSTOMIZING_PURPOSE(HttpStatus.NOT_FOUND, "구매 목적에 부합하는 키워드를 flowerLanguage로 가진 flowerExpression은 있으나 원하는 꽃다발 색상에 부합하지 않습니다."),

	DUPLICATED_FILE_NAME(HttpStatus.CONFLICT, "이미 존재하는 파일 이름입니다."),
	IMAGE_SIZE_LIMIT_ERROR(HttpStatus.FORBIDDEN, "이미지의 크기가 기준을 초과합니다."),
	IMAGE_EXTENSION_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원하지 않는 이미지 파일 형식입니다."),
	NULL_INPUT_CONTENT(HttpStatus.CONFLICT, "이미지 파일이 null로 인식됩니다."),
	IMAGE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다."),
	INVALID_BOUQUET_ID_JSON_FORMAT(HttpStatus.BAD_REQUEST, "이미지 업로드 시 bouquetId를 key로 하는 value가 json(bouquet_id : Long) 형식이어야 합니다."),
	INVALID_BOUQUET_REQUEST_JSON_FORMAT(HttpStatus.BAD_REQUEST, "이미지 업로드 시 request를 key로 하는 value가 json string 형식이어야 합니다."),
	MISMATCH_MEMBER_AND_IMAGE(HttpStatus.BAD_REQUEST, "해당 유저가 수정하려는 이미지를 업로드한 내역이 없습니다. 유저가 업로드한 이미지 아이디로 요청해주세요."),
	NOT_REGISTER_BOUQUET_IMAGE(HttpStatus.NOT_FOUND, "업로드 된 적이 없는 이미지입니다."),
	INVALID_IMAGE_URL(HttpStatus.BAD_REQUEST, "올바른 S3 이미지 경로 URL 형식이 아닙니다.");

	private final HttpStatus status;
	private final String message;
}
