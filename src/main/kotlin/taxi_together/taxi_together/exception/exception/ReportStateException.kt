package taxi_together.taxi_together.exception.exception

import taxi_together.taxi_together.exception.message.RepostStateExceptionMessage

class ReportStateException(val repostStateExceptionMessage: RepostStateExceptionMessage) : RuntimeException(repostStateExceptionMessage.message)