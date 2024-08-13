import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FuncionalidadeDetailComponent } from './funcionalidade-detail.component';

describe('Funcionalidade Management Detail Component', () => {
  let comp: FuncionalidadeDetailComponent;
  let fixture: ComponentFixture<FuncionalidadeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuncionalidadeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FuncionalidadeDetailComponent,
              resolve: { funcionalidade: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FuncionalidadeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FuncionalidadeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load funcionalidade on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FuncionalidadeDetailComponent);

      // THEN
      expect(instance.funcionalidade()).toEqual(expect.objectContaining({ id: 123 }));
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
