package org.example.kmp_shop.presentation.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.example.kmp_shop.domain.model.Product
import org.example.kmp_shop.navigation.LocalNavController
import org.example.kmp_shop.utils.compose.LoadingLayout
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen() {
    val viewModel = koinViewModel<CartViewModel>()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle(CartEvent.Initial)

    CartContent(
        screenState = screenState,
        event = event,
        sendEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartContent(
    screenState: CartScreenState,
    event: CartEvent,
    sendEvent: (CartEvent) -> Unit
) {
    val navController = LocalNavController.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(event) {
        when (event) {
            is CartEvent.Snackbar.ProductRemoveFailure -> {
                snackbarHostState.showSnackbar(message = event.errorMsg)
            }

            is CartEvent.Snackbar.ProductRemovedSuccess -> {
                snackbarHostState.showSnackbar(message = "Product Delete")
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
                    Text(text = "Cart")
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

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = screenState.products,
                    key = { it.id }
                ) { product ->
                    ProductCard(
                        modifier = Modifier.animateItem(),
                        product = product,
                        removeProductClicked = {
                            sendEvent(CartEvent.OnClick.RemoveProduct(product))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    removeProductClicked: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(250.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = product.price.toString() + " ₽",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Red
                )

                Text(
                    text = product.title,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(color = Color.Blue, width = 1.dp),
                    onClick = removeProductClicked
                ) {
                    Text(text = "Remove from Cart")
                }
            }
        }
    }
}