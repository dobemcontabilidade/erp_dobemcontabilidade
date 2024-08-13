import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAgendaContadorConfig } from '../agenda-contador-config.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../agenda-contador-config.test-samples';

import { AgendaContadorConfigService } from './agenda-contador-config.service';

const requireRestSample: IAgendaContadorConfig = {
  ...sampleWithRequiredData,
};

describe('AgendaContadorConfig Service', () => {
  let service: AgendaContadorConfigService;
  let httpMock: HttpTestingController;
  let expectedResult: IAgendaContadorConfig | IAgendaContadorConfig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AgendaContadorConfigService);
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

    it('should create a AgendaContadorConfig', () => {
      const agendaContadorConfig = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(agendaContadorConfig).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AgendaContadorConfig', () => {
      const agendaContadorConfig = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(agendaContadorConfig).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AgendaContadorConfig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AgendaContadorConfig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AgendaContadorConfig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAgendaContadorConfigToCollectionIfMissing', () => {
      it('should add a AgendaContadorConfig to an empty array', () => {
        const agendaContadorConfig: IAgendaContadorConfig = sampleWithRequiredData;
        expectedResult = service.addAgendaContadorConfigToCollectionIfMissing([], agendaContadorConfig);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agendaContadorConfig);
      });

      it('should not add a AgendaContadorConfig to an array that contains it', () => {
        const agendaContadorConfig: IAgendaContadorConfig = sampleWithRequiredData;
        const agendaContadorConfigCollection: IAgendaContadorConfig[] = [
          {
            ...agendaContadorConfig,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAgendaContadorConfigToCollectionIfMissing(agendaContadorConfigCollection, agendaContadorConfig);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AgendaContadorConfig to an array that doesn't contain it", () => {
        const agendaContadorConfig: IAgendaContadorConfig = sampleWithRequiredData;
        const agendaContadorConfigCollection: IAgendaContadorConfig[] = [sampleWithPartialData];
        expectedResult = service.addAgendaContadorConfigToCollectionIfMissing(agendaContadorConfigCollection, agendaContadorConfig);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agendaContadorConfig);
      });

      it('should add only unique AgendaContadorConfig to an array', () => {
        const agendaContadorConfigArray: IAgendaContadorConfig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const agendaContadorConfigCollection: IAgendaContadorConfig[] = [sampleWithRequiredData];
        expectedResult = service.addAgendaContadorConfigToCollectionIfMissing(agendaContadorConfigCollection, ...agendaContadorConfigArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const agendaContadorConfig: IAgendaContadorConfig = sampleWithRequiredData;
        const agendaContadorConfig2: IAgendaContadorConfig = sampleWithPartialData;
        expectedResult = service.addAgendaContadorConfigToCollectionIfMissing([], agendaContadorConfig, agendaContadorConfig2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agendaContadorConfig);
        expect(expectedResult).toContain(agendaContadorConfig2);
      });

      it('should accept null and undefined values', () => {
        const agendaContadorConfig: IAgendaContadorConfig = sampleWithRequiredData;
        expectedResult = service.addAgendaContadorConfigToCollectionIfMissing([], null, agendaContadorConfig, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agendaContadorConfig);
      });

      it('should return initial array if no AgendaContadorConfig is added', () => {
        const agendaContadorConfigCollection: IAgendaContadorConfig[] = [sampleWithRequiredData];
        expectedResult = service.addAgendaContadorConfigToCollectionIfMissing(agendaContadorConfigCollection, undefined, null);
        expect(expectedResult).toEqual(agendaContadorConfigCollection);
      });
    });

    describe('compareAgendaContadorConfig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAgendaContadorConfig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAgendaContadorConfig(entity1, entity2);
        const compareResult2 = service.compareAgendaContadorConfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAgendaContadorConfig(entity1, entity2);
        const compareResult2 = service.compareAgendaContadorConfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAgendaContadorConfig(entity1, entity2);
        const compareResult2 = service.compareAgendaContadorConfig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
