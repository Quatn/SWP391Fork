/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let postContainer = document.getElementById("posts");
//Loading gif by loading.io
let loading =
  '<center class="loading"><img src="./public/images/Ellipsis@1x-2.2s-200px-200px.gif" alt="loading..."/></center>';
//tumbleweed.png by flaticon
let endOfFeed = `
            <center class="endOfFeed">
                <img style="width: 256px" src="./public/images/tumbleweed.png" alt="loading..."/>
                <h3>Looks like we're all out of posts...</h3>
                <h4><a href="#sliders" onclick="resetFeed()">Refresh your feed</a> to see new stuffs!</h4>
            </center>
`;
let apending = false;
let apendingIntervention = false;

function postElement(
  postId,
  userId,
  fullName,
  postTime,
  postTitle,
  CardContent,
  postThumbnail,
) {
  return `
            <div id="post_${postId}" class="card">
                <div class="card-body">
                    <img class="profilePic" src="UserProfile?service=showPic&uId=${userId}">
                    <h5 class="card-title">${fullName}</h5>
                    <i>${postTime}</i>
                </div>
                <div class="container">
                    <a class="post-link" href="blogs/detail?id=${postId}"><h5>${postTitle}</h5></a>
                    <p class="card-text">${CardContent}</p>
                </div>
                <img class="card-img-bottom" src="./public/thumbnails/${postThumbnail}" alt="Card image cap">
            </div>
            `;
}

let firstLoad = true;

let postPageSize = document.currentScript.getAttribute("postPageSize");
if (!postPageSize) postPageSize = 5;

let sortType = 0;

function swapToNew() {
  sortType = 0;
  resetFeed();
}

function swapToHot() {
  sortType = 1;
  resetFeed();
}

function handleFetchPost(resetOffset) {
  switch (sortType) {
    case 0:
      appendNewposts(postPageSize, resetOffset);
      break;

    case 1:
      appendHotposts(postPageSize, resetOffset);
      break;

    default:
      break;
  }
}

function appendNewposts(ammount, resetOffset) {
  if (postContainer) {
    fetch(
      "/QuizPractice/Home?service=newposts" +
        (resetOffset ? "&resetOffset=true" : "") +
        "&ammount=" +
        ammount,
    )
      .then((res) => res.text())
      .then((text) => {
        if (text) {
          firstLoad = false;
          let blogList = JSON.parse(text);
          blogList.forEach((post, index) => {
            postContainer.innerHTML += postElement(
              post.BlogId,
              post.UserId,
              post.FullName,
              post.UpdatedTime,
              post.BlogTitle,
              post.CardContent,
              post.Thumbnail,
            );
          });
        } else {
          if (firstLoad) resetFeed();
          else {
            postContainer.innerHTML += endOfFeed;
            apendingIntervention = true;
          }
        }
      });
  }
}

function appendHotposts(ammount, resetOffset) {
  if (postContainer) {
    fetch(
      "/QuizPractice/Home?service=hotposts" +
        (resetOffset ? "&resetOffset=true" : "") +
        "&ammount=" +
        ammount,
    )
      .then((res) => res.text())
      .then((text) => {
        if (text) {
          firstLoad = false;
          let blogList = JSON.parse(text);
          blogList.forEach((post, index) => {
            postContainer.innerHTML += postElement(
              post.BlogId,
              post.UserId,
              post.FullName,
              post.UpdatedTime,
              post.BlogTitle,
              post.CardContent,
              post.Thumbnail,
            );
          });
        } else {
          if (firstLoad) resetFeed();
          else {
            postContainer.innerHTML += endOfFeed;
            apendingIntervention = true;
          }
        }
      });
  }
}

function resetFeed() {
  postContainer.innerHTML = "";
  apendingIntervention = false;
  handleFetchPost(true);
}

$(window).scroll(async function () {
  if ($(window).scrollTop() + $(window).height() > $(document).height() - 100) {
    if (!apending && !apendingIntervention) {
      apending = true;
      postContainer.innerHTML += loading;
      setTimeout(() => {
        postContainer.querySelector(".loading").remove();
        handleFetchPost(false);
        setTimeout(() => {
          apending = false;
        }, 1000);
      }, 500);
    }
  }
});
