package com.example.util.simpletimetracker.feature_dialogs.cardSize.mapper

import com.example.util.simpletimetracker.core.adapter.ViewHolderType
import com.example.util.simpletimetracker.core.mapper.ColorMapper
import com.example.util.simpletimetracker.core.mapper.RecordTypeViewDataMapper
import com.example.util.simpletimetracker.core.viewData.RecordTypeViewData
import com.example.util.simpletimetracker.domain.model.RecordType
import com.example.util.simpletimetracker.feature_dialogs.cardSize.viewData.CardSizeButtonsViewData
import com.example.util.simpletimetracker.feature_dialogs.cardSize.viewData.CardSizeDefaultButtonViewData
import javax.inject.Inject

class CardSizeViewDataMapper @Inject constructor(
    private val recordTypeViewDataMapper: RecordTypeViewDataMapper,
    private val colorMapper: ColorMapper
) {

    fun toToRecordTypeViewData(
        recordType: RecordType,
        numberOfCards: Int,
        isDarkTheme: Boolean
    ): RecordTypeViewData {
        return recordTypeViewDataMapper.map(recordType, numberOfCards, isDarkTheme)
    }

    fun toToButtonsViewData(numberOfCards: Int): List<ViewHolderType> {
        return (1..6).map { buttonNumber ->
            CardSizeButtonsViewData(
                numberOfCards = buttonNumber,
                name = buttonNumber.toString(),
                isSelected = numberOfCards == buttonNumber
            )
        }
    }

    fun toDefaultButtonViewData(
        numberOfCards: Int,
        isDarkTheme: Boolean
    ): CardSizeDefaultButtonViewData {
        return CardSizeDefaultButtonViewData(
            color = if (numberOfCards == 0) {
                colorMapper.toActiveColor(isDarkTheme)
            } else {
                colorMapper.toInactiveColor(isDarkTheme)
            }
        )
    }
}