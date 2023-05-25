[![image](https://user-images.githubusercontent.com/89020936/229500609-2255d1a0-9332-421b-a6ed-f5c9868c09b5.png)](https://www.youtube.com/watch?v=XELi9FkURXg)


# 💡 Topic

- **초단기예보를 통한 정확성 높은 강수 예보 애플리케이션**
- 현재 위치에 동기화하여 초단기예보 조회
- 직접 설정한 위치에 대해 초단기예보 조회   
<br/> 

# 📝 Summary

 기상청의 초단기 예보 Open api를 활용한 강수 예보 애플리케이션. 향후 6시간 동안의 강수 예측량을 시각화하여 안내해준다. 사용자로부터 위치 권한을 부여받아 현재 위치한 곳의 예보를 조회하는 것에서 나아가 원하는 장소를 선택하여 해당 위치의 예보 또한 조회가능하다. 또한 정확성 높은 초단기 예보를 사용자가 더욱 쉽게 접근할 수 있도록 한다.   
 <br/> 

# ⭐️ Key Function

- **초단기 예보 조회**
    - 설정한 위치에 대해 초단기 예보를 조회한다.
    - 향후 6시간 동안의 예보를 그래프 형식으로 시각화하여 표기한다.
    
    
- **위치 검색**
    - 사용자가 조회하고자 하는 위치를 직접 검색하여 선택한다.
    - Kakao Open api 활용
     
    
- **현재 위치로 설정**
    - 현재 사용자의 위치 정보를 읽는다.   
    
<br/> 

# 🛠 Tech Stack

- Android
    
    `Kotlin`, `Retrofit`, `DataBinding`, `ViewModel`, `LiveData`, `Coroutine`, `DataStore`, `Retrofit`
, `Custom-View`, `Hilt`, `Room`, `Coroutine-Exception-Handler`
    
<br/> 

# ⚙️ Architecture

`MVVM`, `Clean-Architecture` 

<br/> 

# 🧑🏻‍💻 Team

- 안드로이드 개발자 1명
