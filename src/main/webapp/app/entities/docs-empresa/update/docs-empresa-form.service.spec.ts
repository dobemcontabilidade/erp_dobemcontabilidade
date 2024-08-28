import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../docs-empresa.test-samples';

import { DocsEmpresaFormService } from './docs-empresa-form.service';

describe('DocsEmpresa Form Service', () => {
  let service: DocsEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocsEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createDocsEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocsEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            documento: expect.any(Object),
            descricao: expect.any(Object),
            url: expect.any(Object),
            dataEmissao: expect.any(Object),
            dataEncerramento: expect.any(Object),
            orgaoEmissor: expect.any(Object),
            pessoaJuridica: expect.any(Object),
          }),
        );
      });

      it('passing IDocsEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createDocsEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            documento: expect.any(Object),
            descricao: expect.any(Object),
            url: expect.any(Object),
            dataEmissao: expect.any(Object),
            dataEncerramento: expect.any(Object),
            orgaoEmissor: expect.any(Object),
            pessoaJuridica: expect.any(Object),
          }),
        );
      });
    });

    describe('getDocsEmpresa', () => {
      it('should return NewDocsEmpresa for default DocsEmpresa initial value', () => {
        const formGroup = service.createDocsEmpresaFormGroup(sampleWithNewData);

        const docsEmpresa = service.getDocsEmpresa(formGroup) as any;

        expect(docsEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocsEmpresa for empty DocsEmpresa initial value', () => {
        const formGroup = service.createDocsEmpresaFormGroup();

        const docsEmpresa = service.getDocsEmpresa(formGroup) as any;

        expect(docsEmpresa).toMatchObject({});
      });

      it('should return IDocsEmpresa', () => {
        const formGroup = service.createDocsEmpresaFormGroup(sampleWithRequiredData);

        const docsEmpresa = service.getDocsEmpresa(formGroup) as any;

        expect(docsEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocsEmpresa should not enable id FormControl', () => {
        const formGroup = service.createDocsEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocsEmpresa should disable id FormControl', () => {
        const formGroup = service.createDocsEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
