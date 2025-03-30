package org.example.kmp_shop.presentation.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.evaluateY
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.example.kmp_shop.domain.model.Product
import org.example.kmp_shop.navigation.LocalNavController
import org.example.kmp_shop.utils.compose.LoadingLayout
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProductDetailScreen(productId: Int, productTitle: String) {
    val viewModel = koinViewModel<ProductDetailViewModel>(
        parameters = {
            parametersOf(productId)
        }
    )
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle(ProductDetailEvent.Initial)

    ProductDetailContent(
        screenState = screenState,
        event = event,
        sendEvent = viewModel::onEvent,
        productTitle = productTitle
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailContent(
    screenState: ProductDetailScreenState,
    event: ProductDetailEvent,
    sendEvent: (ProductDetailEvent) -> Unit,
    productTitle: String
) {
    val navController = LocalNavController.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(event) {
        when(event) {
            is ProductDetailEvent.Snackbar.ProductAddedFailure -> {
                snackbarHostState.showSnackbar(message = event.errMsg)
            }
            is ProductDetailEvent.Snackbar.ProductAddedToCartSuccess -> {
                snackbarHostState.showSnackbar(message = "Product Added to Cart")
            }
            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black.copy(alpha = 0.1f),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = productTitle,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.clip(CircleShape),
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { scaffoldPadding ->
        LoadingLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            loading = screenState.loading,
            animationMills = 1000,
        ) {
            if (screenState.errorMessage.isNotEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = screenState.errorMessage,
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }
            }

            ProductDetail(
                product = screenState.product,
                addToCartClicked = { sendEvent(ProductDetailEvent.OnClick.AddProductToCart(screenState.product)) }
            )
        }
    }
}

@Composable
private fun ProductDetail(
    product: Product,
    addToCartClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            model = product.image,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            text = product.price.toString() + " ₽",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Red
        )

        Text(
            text = product.title,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = product.description,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            color = Color.Gray
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(color = Color.Blue, width = 1.dp),
                onClick = addToCartClicked
            ) {
                Text(text = "Add to cart")
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                onClick = {}
            ) {
                Text(text = "Buy Now")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}