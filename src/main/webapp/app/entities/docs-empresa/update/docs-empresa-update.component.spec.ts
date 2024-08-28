import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { PessoajuridicaService } from 'app/entities/pessoajuridica/service/pessoajuridica.service';
import { DocsEmpresaService } from '../service/docs-empresa.service';
import { IDocsEmpresa } from '../docs-empresa.model';
import { DocsEmpresaFormService } from './docs-empresa-form.service';

import { DocsEmpresaUpdateComponent } from './docs-empresa-update.component';

describe('DocsEmpresa Management Update Component', () => {
  let comp: DocsEmpresaUpdateComponent;
  let fixture: ComponentFixture<DocsEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let docsEmpresaFormService: DocsEmpresaFormService;
  let docsEmpresaService: DocsEmpresaService;
  let pessoajuridicaService: PessoajuridicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DocsEmpresaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DocsEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocsEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    docsEmpresaFormService = TestBed.inject(DocsEmpresaFormService);
    docsEmpresaService = TestBed.inject(DocsEmpresaService);
    pessoajuridicaService = TestBed.inject(PessoajuridicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pessoajuridica query and add missing value', () => {
      const docsEmpresa: IDocsEmpresa = { id: 456 };
      const pessoaJuridica: IPessoajuridica = { id: 13481 };
      docsEmpresa.pessoaJuridica = pessoaJuridica;

      const pessoajuridicaCollection: IPessoajuridica[] = [{ id: 18146 }];
      jest.spyOn(pessoajuridicaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoajuridicaCollection })));
      const additionalPessoajuridicas = [pessoaJuridica];
      const expectedCollection: IPessoajuridica[] = [...additionalPessoajuridicas, ...pessoajuridicaCollection];
      jest.spyOn(pessoajuridicaService, 'addPessoajuridicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ docsEmpresa });
      comp.ngOnInit();

      expect(pessoajuridicaService.query).toHaveBeenCalled();
      expect(pessoajuridicaService.addPessoajuridicaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoajuridicaCollection,
        ...additionalPessoajuridicas.map(expect.objectContaining),
      );
      expect(comp.pessoajuridicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const docsEmpresa: IDocsEmpresa = { id: 456 };
      const pessoaJuridica: IPessoajuridica = { id: 6937 };
      docsEmpresa.pessoaJuridica = pessoaJuridica;

      activatedRoute.data = of({ docsEmpresa });
      comp.ngOnInit();

      expect(comp.pessoajuridicasSharedCollection).toContain(pessoaJuridica);
      expect(comp.docsEmpresa).toEqual(docsEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocsEmpresa>>();
      const docsEmpresa = { id: 123 };
      jest.spyOn(docsEmpresaFormService, 'getDocsEmpresa').mockReturnValue(docsEmpresa);
      jest.spyOn(docsEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docsEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: docsEmpresa }));
      saveSubject.complete();

      // THEN
      expect(docsEmpresaFormService.getDocsEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(docsEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(docsEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocsEmpresa>>();
      const docsEmpresa = { id: 123 };
      jest.spyOn(docsEmpresaFormService, 'getDocsEmpresa').mockReturnValue({ id: null });
      jest.spyOn(docsEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docsEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: docsEmpresa }));
      saveSubject.complete();

      // THEN
      expect(docsEmpresaFormService.getDocsEmpresa).toHaveBeenCalled();
      expect(docsEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocsEmpresa>>();
      const docsEmpresa = { id: 123 };
      jest.spyOn(docsEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docsEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(docsEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoajuridica', () => {
      it('Should forward to pessoajuridicaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoajuridicaService, 'comparePessoajuridica');
        comp.comparePessoajuridica(entity, entity2);
        expect(pessoajuridicaService.comparePessoajuridica).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
