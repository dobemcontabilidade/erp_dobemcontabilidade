import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFeedBackUsuarioParaContador } from '../feed-back-usuario-para-contador.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../feed-back-usuario-para-contador.test-samples';

import { FeedBackUsuarioParaContadorService } from './feed-back-usuario-para-contador.service';

const requireRestSample: IFeedBackUsuarioParaContador = {
  ...sampleWithRequiredData,
};

describe('FeedBackUsuarioParaContador Service', () => {
  let service: FeedBackUsuarioParaContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IFeedBackUsuarioParaContador | IFeedBackUsuarioParaContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FeedBackUsuarioParaContadorService);
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

    it('should create a FeedBackUsuarioParaContador', () => {
      const feedBackUsuarioParaContador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(feedBackUsuarioParaContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FeedBackUsuarioParaContador', () => {
      const feedBackUsuarioParaContador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(feedBackUsuarioParaContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FeedBackUsuarioParaContador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FeedBackUsuarioParaContador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FeedBackUsuarioParaContador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFeedBackUsuarioParaContadorToCollectionIfMissing', () => {
      it('should add a FeedBackUsuarioParaContador to an empty array', () => {
        const feedBackUsuarioParaContador: IFeedBackUsuarioParaContador = sampleWithRequiredData;
        expectedResult = service.addFeedBackUsuarioParaContadorToCollectionIfMissing([], feedBackUsuarioParaContador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(feedBackUsuarioParaContador);
      });

      it('should not add a FeedBackUsuarioParaContador to an array that contains it', () => {
        const feedBackUsuarioParaContador: IFeedBackUsuarioParaContador = sampleWithRequiredData;
        const feedBackUsuarioParaContadorCollection: IFeedBackUsuarioParaContador[] = [
          {
            ...feedBackUsuarioParaContador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFeedBackUsuarioParaContadorToCollectionIfMissing(
          feedBackUsuarioParaContadorCollection,
          feedBackUsuarioParaContador,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FeedBackUsuarioParaContador to an array that doesn't contain it", () => {
        const feedBackUsuarioParaContador: IFeedBackUsuarioParaContador = sampleWithRequiredData;
        const feedBackUsuarioParaContadorCollection: IFeedBackUsuarioParaContador[] = [sampleWithPartialData];
        expectedResult = service.addFeedBackUsuarioParaContadorToCollectionIfMissing(
          feedBackUsuarioParaContadorCollection,
          feedBackUsuarioParaContador,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(feedBackUsuarioParaContador);
      });

      it('should add only unique FeedBackUsuarioParaContador to an array', () => {
        const feedBackUsuarioParaContadorArray: IFeedBackUsuarioParaContador[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const feedBackUsuarioParaContadorCollection: IFeedBackUsuarioParaContador[] = [sampleWithRequiredData];
        expectedResult = service.addFeedBackUsuarioParaContadorToCollectionIfMissing(
          feedBackUsuarioParaContadorCollection,
          ...feedBackUsuarioParaContadorArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const feedBackUsuarioParaContador: IFeedBackUsuarioParaContador = sampleWithRequiredData;
        const feedBackUsuarioParaContador2: IFeedBackUsuarioParaContador = sampleWithPartialData;
        expectedResult = service.addFeedBackUsuarioParaContadorToCollectionIfMissing(
          [],
          feedBackUsuarioParaContador,
          feedBackUsuarioParaContador2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(feedBackUsuarioParaContador);
        expect(expectedResult).toContain(feedBackUsuarioParaContador2);
      });

      it('should accept null and undefined values', () => {
        const feedBackUsuarioParaContador: IFeedBackUsuarioParaContador = sampleWithRequiredData;
        expectedResult = service.addFeedBackUsuarioParaContadorToCollectionIfMissing([], null, feedBackUsuarioParaContador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(feedBackUsuarioParaContador);
      });

      it('should return initial array if no FeedBackUsuarioParaContador is added', () => {
        const feedBackUsuarioParaContadorCollection: IFeedBackUsuarioParaContador[] = [sampleWithRequiredData];
        expectedResult = service.addFeedBackUsuarioParaContadorToCollectionIfMissing(
          feedBackUsuarioParaContadorCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(feedBackUsuarioParaContadorCollection);
      });
    });

    describe('compareFeedBackUsuarioParaContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFeedBackUsuarioParaContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFeedBackUsuarioParaContador(entity1, entity2);
        const compareResult2 = service.compareFeedBackUsuarioParaContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFeedBackUsuarioParaContador(entity1, entity2);
        const compareResult2 = service.compareFeedBackUsuarioParaContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFeedBackUsuarioParaContador(entity1, entity2);
        const compareResult2 = service.compareFeedBackUsuarioParaContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
