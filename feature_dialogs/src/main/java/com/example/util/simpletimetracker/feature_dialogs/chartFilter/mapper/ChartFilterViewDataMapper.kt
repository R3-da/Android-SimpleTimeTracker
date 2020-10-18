package com.example.util.simpletimetracker.feature_dialogs.chartFilter.mapper

import com.example.util.simpletimetracker.core.mapper.ColorMapper
import com.example.util.simpletimetracker.core.mapper.RecordTypeCardSizeMapper
import com.example.util.simpletimetracker.core.mapper.RecordTypeViewDataMapper
import com.example.util.simpletimetracker.core.repo.ResourceRepo
import com.example.util.simpletimetracker.core.viewData.RecordTypeViewData
import com.example.util.simpletimetracker.domain.model.RecordType
import com.example.util.simpletimetracker.feature_dialogs.R
import javax.inject.Inject

class ChartFilterViewDataMapper @Inject constructor(
    private val colorMapper: ColorMapper,
    private val resourceRepo: ResourceRepo,
    private val recordTypeViewDataMapper: RecordTypeViewDataMapper,
    private val recordTypeCardSizeMapper: RecordTypeCardSizeMapper
) {

    fun map(
        recordType: RecordType,
        typeIdsFiltered: List<Long>,
        numberOfCards: Int,
        isDarkTheme: Boolean
    ): RecordTypeViewData {
        return recordTypeViewDataMapper.map(recordType, numberOfCards, isDarkTheme).copy(
            color = if (recordType.id in typeIdsFiltered) {
                colorMapper.toFilteredColor(isDarkTheme)
            } else {
                recordType.color
                    .let { colorMapper.mapToColorResId(it, isDarkTheme) }
                    .let(resourceRepo::getColor)
            }
        )
    }

    fun mapToUntrackedItem(
        typeIdsFiltered: List<Long>,
        numberOfCards: Int,
        isDarkTheme: Boolean
    ): RecordTypeViewData {
        return RecordTypeViewData(
            id = -1L,
            name = R.string.untracked_time_name
                .let(resourceRepo::getString),
            iconId = R.drawable.unknown,
            color = if (-1L in typeIdsFiltered) {
                colorMapper.toFilteredColor(isDarkTheme)
            } else {
                colorMapper.toUntrackedColor(isDarkTheme)
            },
            width = recordTypeCardSizeMapper.toCardWidth(numberOfCards),
            height = recordTypeCardSizeMapper.toCardHeight(numberOfCards),
            asRow = recordTypeCardSizeMapper.toCardAsRow(numberOfCards)
        )
    }
}