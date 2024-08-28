import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoa-fisica/service/pessoa-fisica.service';
import { DocsPessoaService } from '../service/docs-pessoa.service';
import { IDocsPessoa } from '../docs-pessoa.model';
import { DocsPessoaFormService } from './docs-pessoa-form.service';

import { DocsPessoaUpdateComponent } from './docs-pessoa-update.component';

describe('DocsPessoa Management Update Component', () => {
  let comp: DocsPessoaUpdateComponent;
  let fixture: ComponentFixture<DocsPessoaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let docsPessoaFormService: DocsPessoaFormService;
  let docsPessoaService: DocsPessoaService;
  let pessoaFisicaService: PessoaFisicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DocsPessoaUpdateComponent],
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
      .overrideTemplate(DocsPessoaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocsPessoaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    docsPessoaFormService = TestBed.inject(DocsPessoaFormService);
    docsPessoaService = TestBed.inject(DocsPessoaService);
    pessoaFisicaService = TestBed.inject(PessoaFisicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PessoaFisica query and add missing value', () => {
      const docsPessoa: IDocsPessoa = { id: 456 };
      const pessoa: IPessoaFisica = { id: 18015 };
      docsPessoa.pessoa = pessoa;

      const pessoaFisicaCollection: IPessoaFisica[] = [{ id: 10011 }];
      jest.spyOn(pessoaFisicaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaFisicaCollection })));
      const additionalPessoaFisicas = [pessoa];
      const expectedCollection: IPessoaFisica[] = [...additionalPessoaFisicas, ...pessoaFisicaCollection];
      jest.spyOn(pessoaFisicaService, 'addPessoaFisicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ docsPessoa });
      comp.ngOnInit();

      expect(pessoaFisicaService.query).toHaveBeenCalled();
      expect(pessoaFisicaService.addPessoaFisicaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaFisicaCollection,
        ...additionalPessoaFisicas.map(expect.objectContaining),
      );
      expect(comp.pessoaFisicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const docsPessoa: IDocsPessoa = { id: 456 };
      const pessoa: IPessoaFisica = { id: 30227 };
      docsPessoa.pessoa = pessoa;

      activatedRoute.data = of({ docsPessoa });
      comp.ngOnInit();

      expect(comp.pessoaFisicasSharedCollection).toContain(pessoa);
      expect(comp.docsPessoa).toEqual(docsPessoa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocsPessoa>>();
      const docsPessoa = { id: 123 };
      jest.spyOn(docsPessoaFormService, 'getDocsPessoa').mockReturnValue(docsPessoa);
      jest.spyOn(docsPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docsPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: docsPessoa }));
      saveSubject.complete();

      // THEN
      expect(docsPessoaFormService.getDocsPessoa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(docsPessoaService.update).toHaveBeenCalledWith(expect.objectContaining(docsPessoa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocsPessoa>>();
      const docsPessoa = { id: 123 };
      jest.spyOn(docsPessoaFormService, 'getDocsPessoa').mockReturnValue({ id: null });
      jest.spyOn(docsPessoaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docsPessoa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: docsPessoa }));
      saveSubject.complete();

      // THEN
      expect(docsPessoaFormService.getDocsPessoa).toHaveBeenCalled();
      expect(docsPessoaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocsPessoa>>();
      const docsPessoa = { id: 123 };
      jest.spyOn(docsPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docsPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(docsPessoaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoaFisica', () => {
      it('Should forward to pessoaFisicaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaFisicaService, 'comparePessoaFisica');
        comp.comparePessoaFisica(entity, entity2);
        expect(pessoaFisicaService.comparePessoaFisica).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
