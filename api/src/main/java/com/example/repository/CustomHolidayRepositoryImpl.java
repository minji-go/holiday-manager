package com.example.repository;

import com.example.dto.SearchHolidayDto;
import com.example.entity.Holiday;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.entity.QCountry.country;
import static com.example.entity.QHoliday.holiday;
import static org.springframework.util.StringUtils.hasText;


@Repository
@RequiredArgsConstructor
public class CustomHolidayRepositoryImpl implements CustomHolidayRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Holiday> search(SearchHolidayDto dto) {

        List<Holiday> holidays = queryFactory
                .selectFrom(holiday)
                .join(holiday.country, country).fetchJoin()
                .where(getConditionExpression(dto))
                .offset((long) (dto.page() - 1) * dto.size())
                .limit(dto.size())
                .orderBy(holiday.date.asc())
                .fetch();

        Long total = Optional.ofNullable(queryFactory
                        .select(holiday.count())
                        .from(holiday)
                        .join(holiday.country, country)
                        .where(getConditionExpression(dto))
                        .fetchOne())
                .orElse(0L);

        return new PageImpl<>(holidays, PageRequest.of(dto.page() - 1, dto.size()), total);
    }

    private BooleanExpression[] getConditionExpression(SearchHolidayDto dto) {
        return new BooleanExpression[]{
                yearEq(dto.year()),
                countryCodeEq(dto.countryCode()),
                dateFromGoe(dto.from()),
                dateToLoe(dto.to())
        };
    }

    private BooleanExpression yearEq(Integer year) {
        return year != null ? holiday.date.year().eq(year) : null;
    }

    private BooleanExpression countryCodeEq(String countryCode) {
        return hasText(countryCode) ? holiday.country.code.eq(countryCode) : null;
    }

    private BooleanExpression dateFromGoe(LocalDate from) {
        return from != null ? holiday.date.goe(from) : null;
    }

    private BooleanExpression dateToLoe(LocalDate to) {
        return to != null ? holiday.date.loe(to) : null;
    }
}
