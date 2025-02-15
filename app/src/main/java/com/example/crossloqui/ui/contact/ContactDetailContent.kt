package com.example.crossloqui.ui.contact

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.CrossLoquiTheme
import com.example.crossloqui.R
import com.example.crossloqui.firebase.repositories.Resources

@Composable
fun ContactDetailContent(
    paddingValues: PaddingValues,
    email: String,
    painter: Painter = painterResource(id = R.drawable.baseline_person_24),
    contactDetailViewModel: ContactDetailViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    val contactDetailUiState = contactDetailViewModel.contactDetailUiState

    LaunchedEffect(key1 = Unit) {
        contactDetailViewModel.loadContactDetails(targetUserEmail = email)
        contactDetailViewModel.getCurrentUserDetail()
    }

    when(contactDetailUiState.targetUser) {
        is Resources.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = "No such user",
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "No such user",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
        is Resources.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center)
            )
        }
        is Resources.Success -> {
            LazyColumn(modifier = Modifier.padding(paddingValues = paddingValues)) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp)
                    ) {
                        Image(
                            //TODO Should get the contacts' avatar according to the userId
                            painter = painter,
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(180.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                        )
                    }

                    Text(
                        //TODO get the contacts' name according to the userId
                        text = contactDetailUiState.targetUser.data?.name ?: "Name",
                        modifier = Modifier
                            .padding(0.dp, 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge,
                        maxLines = 1
                    )

                    //if (isFriend)
                    if (contactDetailUiState.currentUser.data?.friendList?.contains(email) == true) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 8.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                IconButton(
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    onClick = { /*TODO start a chat*/ }
                                ) {
                                    Icon(imageVector = Icons.Filled.Textsms, contentDescription = "Start chatting")
                                }
                                Text(text = "message")
                            }

                            //if (haveFollowed)
                            if (contactDetailUiState.currentUser.data.followingUser.contains(email)) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    IconButton(
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        ),
                                        onClick = { /*TODO stop following the user*/ },
                                    ) {
                                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Follow")
                                    }
                                    Text(text = "following")
                                }

                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    IconButton(
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        ),
                                        onClick = { /*TODO follow the user*/ }
                                    ) {
                                        Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "Stop following")
                                    }
                                    Text(text = "follow")
                                }
                            }
                        }
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 8.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                IconButton(
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    onClick = { /*TODO add friend(send request)*/
                                        // 添加逻辑点击添加好友时查询是否已经有好友请求存在，如有，bottomsheet展示请求并提供三个选项：同意，拒绝，稍后处理
                                        showDialog = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.PersonAdd,
                                        contentDescription = "Add friend"
                                    )
                                    if (showDialog) {
                                        ConfirmationDialog() { input ->
                                            contactDetailViewModel.addFriend(input)
                                            showDialog = false
                                        }
                                    }
                                }
                                Text(text = "add friend")
                            }

                            if (contactDetailUiState.currentUser.data?.followingUser?.contains(email) == true) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    IconButton(
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        ),
                                        onClick = { /*TODO stop following the user*/ },
                                    ) {
                                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Follow")
                                    }
                                    Text(text = "following")
                                }

                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    IconButton(
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        ),
                                        onClick = { /*TODO follow the user*/ }
                                    ) {
                                        Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "Stop following")
                                    }
                                    Text(text = "follow")
                                }
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Follower",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    //TODO get the number of people that this user has followed according to userId
                                    text = contactDetailUiState.targetUser.data?.followingCount.toString(),
                                    style = MaterialTheme.typography.titleLarge)
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Following",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    //TODO get the number of followers of this user according to the userId
                                    text = contactDetailUiState.targetUser.data?.followerCount.toString(),
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            }
                        }
                    }

                    //TODO get the basic information of this user according to the userId
                    ExpandableCard(
                        birthday = contactDetailUiState.targetUser.data?.birthday ?: "00001010",
                        gender = contactDetailUiState.targetUser.data?.gender ?: "male",
                        email = contactDetailUiState.targetUser.data?.email ?: "template@mail.com"
                    )

                    HorizontalDivider(modifier = Modifier.padding(0.dp, 8.dp))

                    Text(
                        text = "Recent Post",
                        modifier = Modifier.padding(32.dp, 8.dp),
                    )
                }

                //TODO Need to show the recent post
                items(9) {
                    Card(
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .height(160.dp)
                            .fillMaxWidth(),
                        onClick = {}
                    ) {
                        Text(text = "A Post", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableCard(birthday: String, gender: String, email: String) {
    var informationExpanded by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (informationExpanded) 180f else 0f,
        label = ""
    )

    Card(
        onClick = { informationExpanded = !informationExpanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Information",
                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 16.dp, 16.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Cake, contentDescription = "Birthday")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = birthday)
                }
            }
            Icon(
                imageVector = Icons.Filled.ExpandMore,
                contentDescription = "Show more information",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .rotate(rotationState)
            )
        }
        if (informationExpanded) {
            if ("male" == "male"){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 16.dp, 16.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Male, contentDescription = "Male")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = gender)
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 16.dp, 16.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Female, contentDescription = "Female")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = gender)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp, 8.dp, 16.dp, 16.dp)
            ) {
                Icon(imageVector = Icons.Filled.AlternateEmail, contentDescription = "Email")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = email)
            }
        }
    }
}

@Composable
fun ConfirmationDialog (onClose: (String) -> Unit) {
    var input by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            onClose(input)
        },
        title = {
            Text(text = "Confirm Information")
        },
        text = {
            TextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Input here") }
            )
        },
        dismissButton = {
            Button(onClick = { onClose(input) }) {
                Text("cancel")
            }
        },
        confirmButton = {
            Button(onClick = {
                onClose(input)
            }) {
                Text("send")
            }
        }
    )
}


@Preview(showSystemUi = true)
@Composable
fun ContactDetailContentPreview() {
    CrossLoquiTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = Icons.Default.SearchOff,
                    contentDescription = "No such user",
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "No such user",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}