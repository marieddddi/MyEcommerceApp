package com.formation.myecommerceapp.domain.ui.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutPage(
    onOrderValidated: () -> Unit,
    onBackPressed: () -> Unit
) {
    val viewModel: CheckoutViewModel = koinViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirmation de commande") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Vos informations de livraison", fontSize = 20.sp, style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = viewModel.firstName,
                onValueChange = viewModel::onFirstNameChange,
                label = { Text("Prénom") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = viewModel.lastName,
                onValueChange = viewModel::onLastNameChange,
                label = { Text("Nom") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = viewModel.address,
                onValueChange = viewModel::onAddressChange,
                label = { Text("Adresse") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            OutlinedTextField(
                value = viewModel.phone,
                onValueChange = viewModel::onPhoneChange,
                label = { Text("Téléphone") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true
            )

            if (viewModel.errorMessage != null) {
                Text(
                    text = viewModel.errorMessage!!,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.validateAndSubmit(onOrderValidated) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !viewModel.isProcessing
            ) {
                if (viewModel.isProcessing) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Valider la commande")
                }
            }

            OutlinedButton(
                onClick = onBackPressed,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Annuler")
            }
        }
    }
}
