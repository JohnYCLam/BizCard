package com.example.bizcard

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.bizcard.ui.theme.BizCardTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BizCardTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CreateBizCard()
                }
            }
        }
    }
}

@Composable
fun CreateBizCard() {
    val buttonClickedState = remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(390.dp)
                .padding(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateImageProfile()

                HorizontalDivider(thickness = 1.dp,
                     color = Color.LightGray)

                CreateUserInfo()

                Button(shape = RoundedCornerShape(corner = CornerSize(5.dp)),
                    onClick = {
                        //Log.d("Clicked", "Button Clicked")
                        buttonClickedState.value = !buttonClickedState.value

                }) {
                    Text("Portfolio",
                        )
                }

                if (buttonClickedState.value) {
                    Content()
                } else {
                    Box() {}
                }
            }

        }
    }
}

@Preview
@Composable
fun Content() {
    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(5.dp).background(color = Color.White)
        ) {
        Surface(modifier = Modifier.padding(3.dp).fillMaxHeight().fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(6.dp)),
            border = BorderStroke(width = 2.dp, color = Color.LightGray),
            ) {
            Portfolio(data = listOf("Project 1", "Project 2", "Project 3", "Project 4", "Project 5", "Project 6"))
        }
    }
}

@Composable
fun Portfolio(data: List<String>) {
    LazyColumn { items(data) { item ->
        Card(modifier = Modifier.padding(10.dp).fillMaxWidth(),
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
            Row(modifier = Modifier.padding(15.dp).fillMaxWidth()) {
                CreateImageProfile(modifier = Modifier.size(80.dp))

                Column(modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.Center) {
                    Text(item, fontWeight = FontWeight.Bold)
                    Text("Project", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    } }
}

@Composable
private fun CreateUserInfo() {
    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = "John Lam",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Machine Learning and AI Enthusiast",
            style = MaterialTheme.typography.bodyMedium
        )

        ClickableUrlText("https://www.linkedin.com/in/john-lam-au/")

        ClickableUrlText("https://github.com/JohnYCLam")
    }
}

@Composable
private fun ClickableUrlText(url: String, style: TextStyle = MaterialTheme.typography.bodySmall) {
    val context = LocalContext.current  // Get the current context

    // Build an annotated string with the URL
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(tag = "URL", annotation = url)

        append(url)

        pop()
    }

    // Create a clickable text
    ClickableText(
        text = annotatedString,
        style = style,
        onClick = { offset ->
            // Check if the clicked offset corresponds to the URL
            annotatedString.getStringAnnotations("URL", start = offset, end = offset).firstOrNull()?.let {
                // Open the URL using an Intent
                openUrl(context, url)
            }
        }
    )
}

private fun openUrl(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}


@Composable
private fun CreateImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.LightGray),
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_pic),
            contentDescription = "Profile Picture",
            modifier = modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BizCardTheme {
        CreateBizCard()
    }
}