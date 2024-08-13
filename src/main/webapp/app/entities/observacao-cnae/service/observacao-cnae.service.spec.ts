import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IObservacaoCnae } from '../observacao-cnae.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../observacao-cnae.test-samples';

import { ObservacaoCnaeService } from './observacao-cnae.service';

const requireRestSample: IObservacaoCnae = {
  ...sampleWithRequiredData,
};

describe('ObservacaoCnae Service', () => {
  let service: ObservacaoCnaeService;
  let httpMock: HttpTestingController;
  let expectedResult: IObservacaoCnae | IObservacaoCnae[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ObservacaoCnaeService);
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

    it('should create a ObservacaoCnae', () => {
      const observacaoCnae = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(observacaoCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ObservacaoCnae', () => {
      const observacaoCnae = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(observacaoCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ObservacaoCnae', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ObservacaoCnae', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ObservacaoCnae', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addObservacaoCnaeToCollectionIfMissing', () => {
      it('should add a ObservacaoCnae to an empty array', () => {
        const observacaoCnae: IObservacaoCnae = sampleWithRequiredData;
        expectedResult = service.addObservacaoCnaeToCollectionIfMissing([], observacaoCnae);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(observacaoCnae);
      });

      it('should not add a ObservacaoCnae to an array that contains it', () => {
        const observacaoCnae: IObservacaoCnae = sampleWithRequiredData;
        const observacaoCnaeCollection: IObservacaoCnae[] = [
          {
            ...observacaoCnae,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addObservacaoCnaeToCollectionIfMissing(observacaoCnaeCollection, observacaoCnae);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ObservacaoCnae to an array that doesn't contain it", () => {
        const observacaoCnae: IObservacaoCnae = sampleWithRequiredData;
        const observacaoCnaeCollection: IObservacaoCnae[] = [sampleWithPartialData];
        expectedResult = service.addObservacaoCnaeToCollectionIfMissing(observacaoCnaeCollection, observacaoCnae);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(observacaoCnae);
      });

      it('should add only unique ObservacaoCnae to an array', () => {
        const observacaoCnaeArray: IObservacaoCnae[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const observacaoCnaeCollection: IObservacaoCnae[] = [sampleWithRequiredData];
        expectedResult = service.addObservacaoCnaeToCollectionIfMissing(observacaoCnaeCollection, ...observacaoCnaeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const observacaoCnae: IObservacaoCnae = sampleWithRequiredData;
        const observacaoCnae2: IObservacaoCnae = sampleWithPartialData;
        expectedResult = service.addObservacaoCnaeToCollectionIfMissing([], observacaoCnae, observacaoCnae2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(observacaoCnae);
        expect(expectedResult).toContain(observacaoCnae2);
      });

      it('should accept null and undefined values', () => {
        const observacaoCnae: IObservacaoCnae = sampleWithRequiredData;
        expectedResult = service.addObservacaoCnaeToCollectionIfMissing([], null, observacaoCnae, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(observacaoCnae);
      });

      it('should return initial array if no ObservacaoCnae is added', () => {
        const observacaoCnaeCollection: IObservacaoCnae[] = [sampleWithRequiredData];
        expectedResult = service.addObservacaoCnaeToCollectionIfMissing(observacaoCnaeCollection, undefined, null);
        expect(expectedResult).toEqual(observacaoCnaeCollection);
      });
    });

    describe('compareObservacaoCnae', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareObservacaoCnae(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareObservacaoCnae(entity1, entity2);
        const compareResult2 = service.compareObservacaoCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareObservacaoCnae(entity1, entity2);
        const compareResult2 = service.compareObservacaoCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareObservacaoCnae(entity1, entity2);
        const compareResult2 = service.compareObservacaoCnae(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
