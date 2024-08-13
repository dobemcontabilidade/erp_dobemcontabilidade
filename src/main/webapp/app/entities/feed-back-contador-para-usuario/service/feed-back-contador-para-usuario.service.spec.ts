import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFeedBackContadorParaUsuario } from '../feed-back-contador-para-usuario.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../feed-back-contador-para-usuario.test-samples';

import { FeedBackContadorParaUsuarioService } from './feed-back-contador-para-usuario.service';

const requireRestSample: IFeedBackContadorParaUsuario = {
  ...sampleWithRequiredData,
};

describe('FeedBackContadorParaUsuario Service', () => {
  let service: FeedBackContadorParaUsuarioService;
  let httpMock: HttpTestingController;
  let expectedResult: IFeedBackContadorParaUsuario | IFeedBackContadorParaUsuario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FeedBackContadorParaUsuarioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FeedBackContadorParaUsuario', () => {
      const feedBackContadorParaUsuario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(feedBackContadorParaUsuario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FeedBackContadorParaUsuario', () => {
      const feedBackContadorParaUsuario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(feedBackContadorParaUsuario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FeedBackContadorParaUsuario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FeedBackContadorParaUsuario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FeedBackContadorParaUsuario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFeedBackContadorParaUsuarioToCollectionIfMissing', () => {
      it('should add a FeedBackContadorParaUsuario to an empty array', () => {
        const feedBackContadorParaUsuario: IFeedBackContadorParaUsuario = sampleWithRequiredData;
        expectedResult = service.addFeedBackContadorParaUsuarioToCollectionIfMissing([], feedBackContadorParaUsuario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(feedBackContadorParaUsuario);
      });

      it('should not add a FeedBackContadorParaUsuario to an array that contains it', () => {
        const feedBackContadorParaUsuario: IFeedBackContadorParaUsuario = sampleWithRequiredData;
        const feedBackContadorParaUsuarioCollection: IFeedBackContadorParaUsuario[] = [
          {
            ...feedBackContadorParaUsuario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFeedBackContadorParaUsuarioToCollectionIfMissing(
          feedBackContadorParaUsuarioCollection,
          feedBackContadorParaUsuario,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FeedBackContadorParaUsuario to an array that doesn't contain it", () => {
        const feedBackContadorParaUsuario: IFeedBackContadorParaUsuario = sampleWithRequiredData;
        const feedBackContadorParaUsuarioCollection: IFeedBackContadorParaUsuario[] = [sampleWithPartialData];
        expectedResult = service.addFeedBackContadorParaUsuarioToCollectionIfMissing(
          feedBackContadorParaUsuarioCollection,
          feedBackContadorParaUsuario,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(feedBackContadorParaUsuario);
      });

      it('should add only unique FeedBackContadorParaUsuario to an array', () => {
        const feedBackContadorParaUsuarioArray: IFeedBackContadorParaUsuario[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const feedBackContadorParaUsuarioCollection: IFeedBackContadorParaUsuario[] = [sampleWithRequiredData];
        expectedResult = service.addFeedBackContadorParaUsuarioToCollectionIfMissing(
          feedBackContadorParaUsuarioCollection,
          ...feedBackContadorParaUsuarioArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const feedBackContadorParaUsuario: IFeedBackContadorParaUsuario = sampleWithRequiredData;
        const feedBackContadorParaUsuario2: IFeedBackContadorParaUsuario = sampleWithPartialData;
        expectedResult = service.addFeedBackContadorParaUsuarioToCollectionIfMissing(
          [],
          feedBackContadorParaUsuario,
          feedBackContadorParaUsuario2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(feedBackContadorParaUsuario);
        expect(expectedResult).toContain(feedBackContadorParaUsuario2);
      });

      it('should accept null and undefined values', () => {
        const feedBackContadorParaUsuario: IFeedBackContadorParaUsuario = sampleWithRequiredData;
        expectedResult = service.addFeedBackContadorParaUsuarioToCollectionIfMissing([], null, feedBackContadorParaUsuario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(feedBackContadorParaUsuario);
      });

      it('should return initial array if no FeedBackContadorParaUsuario is added', () => {
        const feedBackContadorParaUsuarioCollection: IFeedBackContadorParaUsuario[] = [sampleWithRequiredData];
        expectedResult = service.addFeedBackContadorParaUsuarioToCollectionIfMissing(
          feedBackContadorParaUsuarioCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(feedBackContadorParaUsuarioCollection);
      });
    });

    describe('compareFeedBackContadorParaUsuario', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFeedBackContadorParaUsuario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFeedBackContadorParaUsuario(entity1, entity2);
        const compareResult2 = service.compareFeedBackContadorParaUsuario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFeedBackContadorParaUsuario(entity1, entity2);
        const compareResult2 = service.compareFeedBackContadorParaUsuario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFeedBackContadorParaUsuario(entity1, entity2);
        const compareResult2 = service.compareFeedBackContadorParaUsuario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
