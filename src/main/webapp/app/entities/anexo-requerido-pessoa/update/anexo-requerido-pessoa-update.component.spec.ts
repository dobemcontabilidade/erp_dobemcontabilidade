import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAnexoPessoa } from 'app/entities/anexo-pessoa/anexo-pessoa.model';
import { AnexoPessoaService } from 'app/entities/anexo-pessoa/service/anexo-pessoa.service';
import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { IAnexoRequeridoPessoa } from '../anexo-requerido-pessoa.model';
import { AnexoRequeridoPessoaService } from '../service/anexo-requerido-pessoa.service';
import { AnexoRequeridoPessoaFormService } from './anexo-requerido-pessoa-form.service';

import { AnexoRequeridoPessoaUpdateComponent } from './anexo-requerido-pessoa-update.component';

describe('AnexoRequeridoPessoa Management Update Component', () => {
  let comp: AnexoRequeridoPessoaUpdateComponent;
  let fixture: ComponentFixture<AnexoRequeridoPessoaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoRequeridoPessoaFormService: AnexoRequeridoPessoaFormService;
  let anexoRequeridoPessoaService: AnexoRequeridoPessoaService;
  let anexoPessoaService: AnexoPessoaService;
  let anexoRequeridoService: AnexoRequeridoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoRequeridoPessoaUpdateComponent],
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
      .overrideTemplate(AnexoRequeridoPessoaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoRequeridoPessoaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoRequeridoPessoaFormService = TestBed.inject(AnexoRequeridoPessoaFormService);
    anexoRequeridoPessoaService = TestBed.inject(AnexoRequeridoPessoaService);
    anexoPessoaService = TestBed.inject(AnexoPessoaService);
    anexoRequeridoService = TestBed.inject(AnexoRequeridoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AnexoPessoa query and add missing value', () => {
      const anexoRequeridoPessoa: IAnexoRequeridoPessoa = { id: 456 };
      const anexoPessoa: IAnexoPessoa = { id: 19661 };
      anexoRequeridoPessoa.anexoPessoa = anexoPessoa;

      const anexoPessoaCollection: IAnexoPessoa[] = [{ id: 14273 }];
      jest.spyOn(anexoPessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: anexoPessoaCollection })));
      const additionalAnexoPessoas = [anexoPessoa];
      const expectedCollection: IAnexoPessoa[] = [...additionalAnexoPessoas, ...anexoPessoaCollection];
      jest.spyOn(anexoPessoaService, 'addAnexoPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoPessoa });
      comp.ngOnInit();

      expect(anexoPessoaService.query).toHaveBeenCalled();
      expect(anexoPessoaService.addAnexoPessoaToCollectionIfMissing).toHaveBeenCalledWith(
        anexoPessoaCollection,
        ...additionalAnexoPessoas.map(expect.objectContaining),
      );
      expect(comp.anexoPessoasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AnexoRequerido query and add missing value', () => {
      const anexoRequeridoPessoa: IAnexoRequeridoPessoa = { id: 456 };
      const anexoRequerido: IAnexoRequerido = { id: 32744 };
      anexoRequeridoPessoa.anexoRequerido = anexoRequerido;

      const anexoRequeridoCollection: IAnexoRequerido[] = [{ id: 32514 }];
      jest.spyOn(anexoRequeridoService, 'query').mockReturnValue(of(new HttpResponse({ body: anexoRequeridoCollection })));
      const additionalAnexoRequeridos = [anexoRequerido];
      const expectedCollection: IAnexoRequerido[] = [...additionalAnexoRequeridos, ...anexoRequeridoCollection];
      jest.spyOn(anexoRequeridoService, 'addAnexoRequeridoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoPessoa });
      comp.ngOnInit();

      expect(anexoRequeridoService.query).toHaveBeenCalled();
      expect(anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing).toHaveBeenCalledWith(
        anexoRequeridoCollection,
        ...additionalAnexoRequeridos.map(expect.objectContaining),
      );
      expect(comp.anexoRequeridosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoRequeridoPessoa: IAnexoRequeridoPessoa = { id: 456 };
      const anexoPessoa: IAnexoPessoa = { id: 1072 };
      anexoRequeridoPessoa.anexoPessoa = anexoPessoa;
      const anexoRequerido: IAnexoRequerido = { id: 11273 };
      anexoRequeridoPessoa.anexoRequerido = anexoRequerido;

      activatedRoute.data = of({ anexoRequeridoPessoa });
      comp.ngOnInit();

      expect(comp.anexoPessoasSharedCollection).toContain(anexoPessoa);
      expect(comp.anexoRequeridosSharedCollection).toContain(anexoRequerido);
      expect(comp.anexoRequeridoPessoa).toEqual(anexoRequeridoPessoa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoPessoa>>();
      const anexoRequeridoPessoa = { id: 123 };
      jest.spyOn(anexoRequeridoPessoaFormService, 'getAnexoRequeridoPessoa').mockReturnValue(anexoRequeridoPessoa);
      jest.spyOn(anexoRequeridoPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequeridoPessoa }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoPessoaFormService.getAnexoRequeridoPessoa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoRequeridoPessoaService.update).toHaveBeenCalledWith(expect.objectContaining(anexoRequeridoPessoa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoPessoa>>();
      const anexoRequeridoPessoa = { id: 123 };
      jest.spyOn(anexoRequeridoPessoaFormService, 'getAnexoRequeridoPessoa').mockReturnValue({ id: null });
      jest.spyOn(anexoRequeridoPessoaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoPessoa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequeridoPessoa }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoPessoaFormService.getAnexoRequeridoPessoa).toHaveBeenCalled();
      expect(anexoRequeridoPessoaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoPessoa>>();
      const anexoRequeridoPessoa = { id: 123 };
      jest.spyOn(anexoRequeridoPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoRequeridoPessoaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAnexoPessoa', () => {
      it('Should forward to anexoPessoaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(anexoPessoaService, 'compareAnexoPessoa');
        comp.compareAnexoPessoa(entity, entity2);
        expect(anexoPessoaService.compareAnexoPessoa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAnexoRequerido', () => {
      it('Should forward to anexoRequeridoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(anexoRequeridoService, 'compareAnexoRequerido');
        comp.compareAnexoRequerido(entity, entity2);
        expect(anexoRequeridoService.compareAnexoRequerido).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
