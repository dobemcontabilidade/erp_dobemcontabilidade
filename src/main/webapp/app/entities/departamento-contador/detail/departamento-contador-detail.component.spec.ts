import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DepartamentoContadorDetailComponent } from './departamento-contador-detail.component';

describe('DepartamentoContador Management Detail Component', () => {
  let comp: DepartamentoContadorDetailComponent;
  let fixture: ComponentFixture<DepartamentoContadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepartamentoContadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DepartamentoContadorDetailComponent,
              resolve: { departamentoContador: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DepartamentoContadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartamentoContadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load departamentoContador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DepartamentoContadorDetailComponent);

      // THEN
      expect(instance.departamentoContador()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
