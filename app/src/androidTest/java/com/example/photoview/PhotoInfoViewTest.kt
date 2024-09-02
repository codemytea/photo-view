package com.example.photoview

import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.photoview.api.DTOs.photoInfo.BasePhotoInfoDTO
import com.example.photoview.api.DTOs.photoInfo.DescriptionDTO
import com.example.photoview.api.DTOs.photoInfo.PhotoInfoDTO
import com.example.photoview.api.DTOs.photoInfo.TitleDTO
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.api.DTOs.search.PhotosDTO
import com.example.photoview.api.DTOs.search.SearchDTO
import com.example.photoview.utils.imageBitmap
import com.example.photoview.view.photoInfo.PhotoView
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoInfoViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun photoDoesDisplay() {
        val photoDTO = mockPhotoDTO(mId = 100L, mSecret = "secret")
        mockkStatic(ByteArray::imageBitmap)
        every { any<ByteArray>().imageBitmap() } returns testBitmap().asImageBitmap()

        val flickrAPI = mockkFlickrApiPhotoInfo(PhotoInfoDTO())

        composeTestRule.setContent {
            PhotoView(onHome = {}, photoDTO)
        }

        composeTestRule.onNode(hasContentDescription("Flickr Image")).assertIsDisplayed()

        coVerify {
            photoDTO.getThumbnail()
            flickrAPI.photoInfo(100L, "secret")
        }
    }

    @Test
    fun titleDoesDisplay() {
        val flickrAPI = mockkFlickrApiPhotoInfo(
            PhotoInfoDTO(
                BasePhotoInfoDTO(
                    title = TitleDTO(
                        "test title"
                    )
                )
            )
        )

        composeTestRule.setContent {
            PhotoView(onHome = {}, PhotoDTO())
        }

        composeTestRule.onNodeWithText("test title").assertIsDisplayed()

        coVerify {
            flickrAPI.photoInfo(any(), any())
        }
    }

    @Test
    fun descriptionDoesDisplay() {
        val flickrAPI = mockkFlickrApiPhotoInfo(
            PhotoInfoDTO(
                BasePhotoInfoDTO(
                    description = DescriptionDTO(
                        "test description"
                    )
                )
            )
        )

        composeTestRule.setContent {
            PhotoView(onHome = {}, PhotoDTO())
        }

        composeTestRule.onNodeWithText("test description").assertIsDisplayed()

        coVerify {
            flickrAPI.photoInfo(any(), any())
        }
    }

    @Test
    fun viewsDoDisplay() {
        val flickrAPI = mockkFlickrApiPhotoInfo(
            PhotoInfoDTO(
                BasePhotoInfoDTO(
                    views = "0"
                )
            )
        )

        composeTestRule.setContent {
            PhotoView(onHome = {}, PhotoDTO())
        }

        composeTestRule.onNodeWithText("Views: 0").assertIsDisplayed()
        coVerify {
            flickrAPI.photoInfo(any(), any())
        }
    }

    @Test
    fun dateUploadedDoesDisplay() {
        val flickrAPI = mockkFlickrApiPhotoInfo(
            PhotoInfoDTO(
                BasePhotoInfoDTO(
                    dateuploaded = 1725111100
                )
            )
        )

        composeTestRule.setContent {
            PhotoView(onHome = {}, PhotoDTO())
        }

        composeTestRule.onNodeWithText("Date Uploaded: 31/08/2024").assertIsDisplayed()
        coVerify {
            flickrAPI.photoInfo(any(), any())
        }
    }
}