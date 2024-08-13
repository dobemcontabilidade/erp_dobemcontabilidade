import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../segmento-cnae.test-samples';

import { SegmentoCnaeFormService } from './segmento-cnae-form.service';

describe('SegmentoCnae Form Service', () => {
  let service: SegmentoCnaeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SegmentoCnaeFormService);
  });

  describe('Service methods', () => {
    describe('createSegmentoCnaeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSegmentoCnaeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            icon: expect.any(Object),
            imagem: expect.any(Object),
            tags: expect.any(Object),
            tipo: expect.any(Object),
            subclasseCnaes: expect.any(Object),
            ramo: expect.any(Object),
            empresas: expect.any(Object),
            empresaModelos: expect.any(Object),
          }),
        );
      });

      it('passing ISegmentoCnae should create a new form with FormGroup', () => {
        const formGroup = service.createSegmentoCnaeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            icon: expect.any(Object),
            imagem: expect.any(Object),
            tags: expect.any(Object),
            tipo: expect.any(Object),
            subclasseCnaes: expect.any(Object),
            ramo: expect.any(Object),
            empresas: expect.any(Object),
            empresaModelos: expect.any(Object),
          }),
        );
      });
    });

    describe('getSegmentoCnae', () => {
      it('should return NewSegmentoCnae for default SegmentoCnae initial value', () => {
        const formGroup = service.createSegmentoCnaeFormGroup(sampleWithNewData);

        const segmentoCnae = service.getSegmentoCnae(formGroup) as any;

        expect(segmentoCnae).toMatchObject(sampleWithNewData);
      });

      it('should return NewSegmentoCnae for empty SegmentoCnae initial value', () => {
        const formGroup = service.createSegmentoCnaeFormGroup();

        const segmentoCnae = service.getSegmentoCnae(formGroup) as any;

        expect(segmentoCnae).toMatchObject({});
      });

      it('should return ISegmentoCnae', () => {
        const formGroup = service.createSegmentoCnaeFormGroup(sampleWithRequiredData);

        const segmentoCnae = service.getSegmentoCnae(formGroup) as any;

        expect(segmentoCnae).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISegmentoCnae should not enable id FormControl', () => {
        const formGroup = service.createSegmentoCnaeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSegmentoCnae should disable id FormControl', () => {
        const formGroup = service.createSegmentoCnaeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
