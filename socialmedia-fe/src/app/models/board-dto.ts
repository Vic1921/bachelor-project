export interface BoardDto {
  id: string;
  name: string;
  description: string;
  collaboratorCount: number;
  followerCount: number;
  createdAt: string;
  thumbnailUrl: string;
  pinThumbnailUrls: string[];
}
