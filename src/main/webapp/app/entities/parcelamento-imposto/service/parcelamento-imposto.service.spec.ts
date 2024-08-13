import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IParcelamentoImposto } from '../parcelamento-imposto.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../parcelamento-imposto.test-samples';

import { ParcelamentoImpostoService } from './parcelamento-imposto.service';

const requireRestSample: IParcelamentoImposto = {
  ...sampleWithRequiredData,
};

describe('ParcelamentoImposto Service', () => {
  let service: ParcelamentoImpostoService;
  let httpMock: HttpTestingController;
  let expectedResult: IParcelamentoImposto | IParcelamentoImposto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ParcelamentoImpostoService);
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

    it('should create a ParcelamentoImposto', () => {
      const parcelamentoImposto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(parcelamentoImposto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ParcelamentoImposto', () => {
      const parcelamentoImposto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(parcelamentoImposto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ParcelamentoImposto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ParcelamentoImposto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ParcelamentoImposto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addParcelamentoImpostoToCollectionIfMissing', () => {
      it('should add a ParcelamentoImposto to an empty array', () => {
        const parcelamentoImposto: IParcelamentoImposto = sampleWithRequiredData;
        expectedResult = service.addParcelamentoImpostoToCollectionIfMissing([], parcelamentoImposto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parcelamentoImposto);
      });

      it('should not add a ParcelamentoImposto to an array that contains it', () => {
        const parcelamentoImposto: IParcelamentoImposto = sampleWithRequiredData;
        const parcelamentoImpostoCollection: IParcelamentoImposto[] = [
          {
            ...parcelamentoImposto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addParcelamentoImpostoToCollectionIfMissing(parcelamentoImpostoCollection, parcelamentoImposto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ParcelamentoImposto to an array that doesn't contain it", () => {
        const parcelamentoImposto: IParcelamentoImposto = sampleWithRequiredData;
        const parcelamentoImpostoCollection: IParcelamentoImposto[] = [sampleWithPartialData];
        expectedResult = service.addParcelamentoImpostoToCollectionIfMissing(parcelamentoImpostoCollection, parcelamentoImposto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parcelamentoImposto);
      });

      it('should add only unique ParcelamentoImposto to an array', () => {
        const parcelamentoImpostoArray: IParcelamentoImposto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const parcelamentoImpostoCollection: IParcelamentoImposto[] = [sampleWithRequiredData];
        expectedResult = service.addParcelamentoImpostoToCollectionIfMissing(parcelamentoImpostoCollection, ...parcelamentoImpostoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const parcelamentoImposto: IParcelamentoImposto = sampleWithRequiredData;
        const parcelamentoImposto2: IParcelamentoImposto = sampleWithPartialData;
        expectedResult = service.addParcelamentoImpostoToCollectionIfMissing([], parcelamentoImposto, parcelamentoImposto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parcelamentoImposto);
        expect(expectedResult).toContain(parcelamentoImposto2);
      });

      it('should accept null and undefined values', () => {
        const parcelamentoImposto: IParcelamentoImposto = sampleWithRequiredData;
        expectedResult = service.addParcelamentoImpostoToCollectionIfMissing([], null, parcelamentoImposto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parcelamentoImposto);
      });

      it('should return initial array if no ParcelamentoImposto is added', () => {
        const parcelamentoImpostoCollection: IParcelamentoImposto[] = [sampleWithRequiredData];
        expectedResult = service.addParcelamentoImpostoToCollectionIfMissing(parcelamentoImpostoCollection, undefined, null);
        expect(expectedResult).toEqual(parcelamentoImpostoCollection);
      });
    });

    describe('compareParcelamentoImposto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareParcelamentoImposto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareParcelamentoImposto(entity1, entity2);
        const compareResult2 = service.compareParcelamentoImposto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareParcelamentoImposto(entity1, entity2);
        const compareResult2 = service.compareParcelamentoImposto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareParcelamentoImposto(entity1, entity2);
        const compareResult2 = service.compareParcelamentoImposto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
