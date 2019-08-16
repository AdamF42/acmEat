from enum import Enum


class Status(Enum):
    ACCEPTED = 1
    NOT_ACCEPTED = 2
    ABORTED = 3
    AVAILABLE = 4
    NOT_AVAILABLE = 5
    PENDING = 6